import {Injectable} from '@angular/core';

const DATA_SOURCE_CONNECTION = 'DATA_SOURCE_CONNECTION'

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  public get dataSourceConnection() {
    return localStorage.getItem(DATA_SOURCE_CONNECTION);
  }

  public set dataSourceConnection(dataSource: string) {
    localStorage.setItem(DATA_SOURCE_CONNECTION, dataSource);
  }
}
