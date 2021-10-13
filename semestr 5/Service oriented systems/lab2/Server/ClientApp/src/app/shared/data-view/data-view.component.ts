import { Component, Input, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ApiService } from "../../core/services/api.service";
import { takeUntil } from "rxjs/operators";
import { Subject } from "rxjs";
import { NotificationService } from "../../core/services/notification.service";
import { AgGridAngular } from "ag-grid-angular";
import { CellValueChangedEvent} from "ag-grid-community";
import * as uuid from 'uuid';

interface fieldTable {
  field: string;
  sortable: boolean;
  filter: boolean;
  checkboxSelection: boolean;
  cellEditor: string;
  editable: boolean;
}

const CHANGES = {
  editedRow: [],
  addedRow: [],
  removedRow: [],
};

@Component({
  selector: "data-view",
  templateUrl: "./data-view.component.html",
  styleUrls: ["./data-view.component.css"],
})
export class DataViewComponent implements OnInit, OnDestroy {
  @Input() data;
  @Input() tableName;
  @Input() saving;
  // @ts-ignore
  @ViewChild("agGrid") private agGrid: AgGridAngular;
  private destroy = new Subject();
  isEdited = false;
  bodyTable;
  headerTable: Array<any> = [];

  constructor(
    private api: ApiService,
    private notification: NotificationService
  ) {}

  ngOnInit(): void {
    let keys = Object.keys(this.data[0]);
    keys.forEach((item) => {
      this.headerTable.push({
        field: item,
        sortable: true,
        filter: true,
        checkboxSelection: item == keys[0],
        cellEditor: "agTextCellEditor",
        editable: true,
      } as fieldTable);
    });
    this.bodyTable = this.data;
  }

  savingChanges() {
    if (CHANGES.editedRow.length != 0) {
      this.api
        .updateTableData(CHANGES.editedRow, this.tableName)
        .pipe(takeUntil(this.destroy))
        .subscribe(
          (_) => {
            this.notification.showSuccess("Success saving edited rows!", "");
          },
          (error) => {
            console.log(error);
            this.notification.showError(error, "Error! Incorrect data");
          }
        );
    }
    if (CHANGES.removedRow.length != 0) {
      let columnName = this.headerTable[0].field;
      CHANGES.removedRow.forEach((item) => {
        this.api
          .deleteRow(columnName, item[columnName], this.tableName)
          .pipe(takeUntil(this.destroy))
          .subscribe(
            (_) => {
              this.notification.showSuccess("Success saving removed rows!", "");
            },
            (error) => {
              console.log(error);
              this.notification.showError(error, "Error! Incorrect data");
            }
          );
      });
    }
    if (CHANGES.addedRow.length != 0) {
      this.api
        .insertTableData(CHANGES.addedRow, this.tableName)
        .pipe(takeUntil(this.destroy))
        .subscribe(
          (_) => {
            this.notification.showSuccess("Success saving added rows!", "");
          },
          (error) => {
            console.log(error);
            this.notification.showError(error, "Error! Incorrect data");
          }
        );
    }
  }

  cellValueChanged($event: CellValueChangedEvent) {
    this.isEdited = true;
    let ID = this.headerTable[0].field;
    let isConsist = -1;

    if ($event.colDef.field == ID) {
      if ($event.data[ID] != undefined) {
        let oldRow = Object.assign({}, $event.data);
        oldRow[ID] = $event.oldValue;

        isConsist = this.isConsistObject(CHANGES.removedRow, ID, oldRow[ID]);
        if (isConsist == -1) {
          CHANGES.removedRow.push(oldRow);
        } else {
          CHANGES.removedRow[isConsist] = oldRow;
        }

        isConsist = this.isConsistObject(CHANGES.addedRow, ID, oldRow[ID]);
        if (isConsist == -1) {
          CHANGES.addedRow.push($event.data);
        }
      }
    } else {
      isConsist =
        this.isConsistObject(CHANGES.addedRow, ID, $event.data[ID]) == -1
          ? this.isConsistObject(CHANGES.editedRow, ID, $event.data[ID])
          : 0;
      if (isConsist == -1) {
        CHANGES.editedRow.push($event.data);
      }
    }

    console.log("CHANGES", CHANGES || "sdasd");
  }

  isConsistObject(array, key: string, value: string) {
    let isConsist = -1;
    array.forEach((item, i) => {
      if (item[key] == value) {
        isConsist = i;
        return;
      }
    });
    return isConsist;
  }

  onAddRow() {
    let ID = this.headerTable[0].field;
    let row = {};
    row[ID] = uuid.v4();
    this.agGrid.api.applyTransaction({ add: [row] });
  }

  onDeleteRow() {
    let selectedData = this.agGrid.api.getSelectedRows();
    console.log(selectedData);
    this.agGrid.api.applyTransaction({ remove: selectedData });
    selectedData.forEach((item) => {
      CHANGES.removedRow.push(item);
    });
    this.isEdited = true;
  }

  ngOnDestroy() {
    this.destroy.next(true);
    this.bodyTable = [];
    this.headerTable = [];
    console.log("DESTROY")
  }
}
