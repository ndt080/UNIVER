package tourism;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class SimpleGUI {
    private static String encoding = "Cp866";
    private JFrame frame, inputFrame, fontFrame;
    private final JTextArea textArea = new JTextArea();
    private JTextArea clientChoice, dateArea, timeArea, placeArea, nameArea, numberArea, bagadgeNumberArea, bagadgeWeightArea, removeArea;
    private JComboBox<String> argsSearchComboBox, typeSearchComboBox;

    static final String filename    = "Tourisms.dat";
    static final String filenameBak = "Tourisms.~dat";
    static final String idxname     = "Tourisms.idx";
    static final String idxnameBak  = "Tourisms.~idx";

    static ArrayList<Tourism> array;
    public void Show(){
        frame = new JFrame("AVIALINE MANAGER");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new closeListener());

        JPanel clientChoicePanelCommon = new JPanel();
        JPanel clientChoicePanel = new JPanel();
        JMenuBar menuBar = new JMenuBar();

        //////////////////////////////////////////////////////////////////////////////////
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        JMenuItem quit = new JMenuItem("Сохранить данные и выйти");
        quit.addActionListener(new quitListener());
        fileMenu.add(quit);
        //////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////
        JMenu printSorted = new JMenu("Сортировать");
        menuBar.add(printSorted);

        JMenuItem printSorted1 = new JMenuItem("По номеру");
        printSorted1.addActionListener(new printSortedListener("number", false));
        JMenuItem printSorted2 = new JMenuItem("По дате");
        printSorted2.addActionListener(new printSortedListener("date", false));
        JMenuItem printSorted3 = new JMenuItem("По времени");
        printSorted3.addActionListener(new printSortedListener("time", false));
        JMenuItem printSorted4 = new JMenuItem("По месту прибытия");
        printSorted4.addActionListener(new printSortedListener("place", false));
        JMenuItem printSorted5 = new JMenuItem("По количеству багажа");
        printSorted5.addActionListener(new printSortedListener("weight", false));

        printSorted.add(printSorted1);
        printSorted.add(printSorted2);
        printSorted.add(printSorted3);
        printSorted.add(printSorted4);
        printSorted.add(printSorted5);
        ///////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////
        String[] items = {
                "по ключу",
                "по ключу > указанного",
                "по ключу < указанного"
        };
        argsSearchComboBox = new JComboBox<>(items);
        argsSearchComboBox.setEditable(true);

        String[] items2 = {
                "number",
                "date",
                "time",
                "place",
                "weight"
        };
        typeSearchComboBox = new JComboBox<>(items2);
        typeSearchComboBox.setEditable(true);

        clientChoice = new JTextArea();
        clientChoice.setRows(1);
        clientChoice.setColumns(15);

        JButton buttonSearch = new JButton("Поиск");
        buttonSearch.addActionListener(new searchByKey());

        clientChoicePanel.add(argsSearchComboBox);
        clientChoicePanel.add(typeSearchComboBox);
        clientChoicePanel.add(clientChoice);
        clientChoicePanel.add(buttonSearch);
        ///////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////
        JButton button = new JButton("Все записи");
        button.addActionListener(new printDefaultListener());
        clientChoicePanelCommon.add(button);
        //////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////
        JButton AddRecordBTN = new JButton("Добавить");
        AddRecordBTN.addActionListener(new appendItemListener());
        clientChoicePanelCommon.add(AddRecordBTN);
        ///////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////
        JMenu deleteMenu = new JMenu("Инструменты");
        JMenuItem deleteAll = new JMenuItem("Удалить все записи");
        deleteAll.addActionListener(new deleteAllListener());
        deleteMenu.add(deleteAll);

        menuBar.add(deleteMenu);

        removeArea = new JTextArea();
        removeArea.setRows(1);
        removeArea.setColumns(15);

        JButton buttonRemove = new JButton("Delete");
        buttonRemove.addActionListener(new deleteByIndexListener());
        clientChoicePanelCommon.add(removeArea);
        clientChoicePanelCommon.add(buttonRemove);

        ///////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////
        JMenu viewMenu = new JMenu("View");
        JMenuItem font = new JMenuItem("Font");
        viewMenu.add(font);
        font.addActionListener(new fontListener());
        menuBar.add(viewMenu);
        ///////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////
        JMenu helpMenu = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        helpMenu.add(about);
        about.addActionListener(new helpListener());
        menuBar.add(helpMenu);
        ///////////////////////////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////////////////////////////
        JScrollPane scroller = new JScrollPane(textArea);
        textArea.setEditable(false);

        frame.getContentPane().add(BorderLayout.CENTER, scroller);
        frame.getContentPane().add(BorderLayout.NORTH, clientChoicePanel);
        frame.getContentPane().add(BorderLayout.SOUTH, clientChoicePanelCommon);

        frame.setLocation(300, 50);
        frame.setJMenuBar(menuBar);
        frame.setSize(600,600);
        frame.setVisible(true);
        //////////////////////////////////////////////////////////////////////////////////
        readArray();
    }


    //////////////////////////////// PRINT METHODS ///////////////////////////////////////
    class printDefaultListener implements ActionListener{
        void printFile() throws IOException, ClassNotFoundException {
            long pos;
            int rec = 0;
            try ( RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
                while (( pos = raf.getFilePointer()) < raf.length() ) {
                    textArea.append("#" + (++rec ));
                    printRecord( raf, pos );
                }
                System.out.flush();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e){
            try {
                textArea.setText(null);
                printFile();
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    class printSortedListener implements ActionListener{
        private final String args;
        private final boolean reverse;

        public printSortedListener(String args, boolean reverse) {
            this.args = args;
            this.reverse = reverse;
        }
        boolean printFile(String args, boolean reverse )
                throws ClassNotFoundException, IOException {

            try ( Index idx = Index.load( idxname );
                  RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
                IndexBase pidx = indexByArg( args, idx );
                if ( pidx == null ) {
                    return false;
                }
                String[] keys = pidx.getKeys( reverse ? new KeyCompReverse() : new KeyComp() );
                for ( String key : keys ) {
                    printRecord( raf, key, pidx );
                }
            }
            return true;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            try {
                textArea.setText(null);
                printFile(args, reverse);
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void printRecord( RandomAccessFile raf, long pos )
            throws ClassNotFoundException, IOException {
        boolean[] wasZipped = new boolean[] {false};
        Tourism book = (Tourism) Buffer.readObject( raf, pos, wasZipped );
        if (wasZipped[0]) {
            textArea.append( " compressed" );
        }
        textArea.append( " record at position "+ pos + ": \n" + book  + "\n");
    }
    private void printRecord( RandomAccessFile raf,String key,IndexBase pidx )
            throws ClassNotFoundException, IOException {
        long[] poss = pidx.get( key );
        for ( long pos : poss ) {
            textArea.append( "*** Key: " +  key + " points to" );
            printRecord( raf, pos );
        }
    }

    //////////////////////////////// SEARCH METHODS ///////////////////////////////////////
    class searchByKey implements ActionListener{
        private String arg;
        private String type;
        private String key;

        public searchByKey() {
            this.arg = null;
            this.type = null;
            this.key = null;
        }
        boolean findByKey(String type, String key ) throws ClassNotFoundException, IOException {
            try ( Index idx = Index.load( idxname );
              RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
                IndexBase pidx = indexByArg( type, idx );
                if (!pidx.contains(key)) {
                    textArea.append("Key not found: " + key );
                    return false;
                }
                printRecord( raf, key, pidx );
            }
            return true;
        }

        boolean findByKey( String type, String key, Comparator<String> comp )
                throws ClassNotFoundException, IOException {
            try ( Index idx = Index.load( idxname );
                  RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
                IndexBase pidx = indexByArg( type, idx );
                if (!pidx.contains(key)) {
                    textArea.append("Key not found: " + key );
                    return false;
                }
                String[] keys = pidx.getKeys( comp );
                for (String k : keys) {
                    if (k.equals(key)) {
                        break;
                    }
                    printRecord(raf, k, pidx);
                }
            }
            return true;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            try {
                String[] items = {
                        "по ключу",
                        "по ключу > указанного",
                        "по ключу < указанного"
                };

                textArea.setText(null);
                key = clientChoice.getText();
                type = (String) typeSearchComboBox.getSelectedItem();
                arg = (String) argsSearchComboBox.getSelectedItem();
                if(arg.equals(items[0])){
                    findByKey(type, key);
                }
                if(arg.equals(items[1])){
                    findByKey(type, key, new KeyCompReverse());
                }
                if(arg.equals(items[2])){
                    findByKey(type, key, new KeyComp());
                }
            } catch (ClassNotFoundException | IOException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }

        }
    }

    //////////////////////////////// ADD RECORD METHODS ///////////////////////////////////////
    class appendItemListener implements ActionListener {
        private void createInputFrame(){
            inputFrame = new JFrame("Введите данные");
            inputFrame.setVisible(true);
            inputFrame.setSize(400,250);
            JPanel inputPanel = new JPanel();

            JLabel dateLabel = new JLabel("Дата рейса: ");
            dateArea = new JTextArea();
            dateArea.setColumns(25);
            inputFrame.getContentPane().add(BorderLayout.EAST, dateArea);

            JLabel timeLabel = new JLabel("Время рейса: ");
            timeArea = new JTextArea();
            timeArea.setColumns(25);

            JLabel placeLabel = new JLabel("Пункт назначения: ");
            placeArea = new JTextArea();
            placeArea.setColumns(25);

            JLabel nameLabel = new JLabel("Имя пассажира: ");
            nameArea = new JTextArea();
            nameArea.setColumns(25);

            JLabel numberLabel = new JLabel("Номер рейса: ");
            numberArea = new JTextArea();
            numberArea.setColumns(25);

            JLabel bagadgeNumberLabel = new JLabel("К-во мест багажа: ");
            bagadgeNumberArea = new JTextArea();
            bagadgeNumberArea.setColumns(25);

            JLabel bagadgeWeightLabel = new JLabel("Вес багажа: ");
            bagadgeWeightArea = new JTextArea();
            bagadgeWeightArea.setColumns(25);

            JButton appendItemBtn = new JButton("Добавить запись");
            appendItemBtn.addActionListener(new appendItemBtnListener());
            inputPanel.add(dateLabel);
            inputPanel.add(dateArea);
            inputPanel.add(timeLabel);
            inputPanel.add(timeArea);
            inputPanel.add(placeLabel);
            inputPanel.add(placeArea);
            inputPanel.add(nameLabel);
            inputPanel.add(nameArea);
            inputPanel.add(numberLabel);
            inputPanel.add(numberArea);
            inputPanel.add(bagadgeNumberLabel);
            inputPanel.add(bagadgeNumberArea);
            inputPanel.add(bagadgeWeightLabel);
            inputPanel.add(bagadgeWeightArea);
            inputPanel.add(appendItemBtn);
            inputFrame.getContentPane().add(BorderLayout.CENTER, inputPanel);
        }
        @Override
        public void actionPerformed(ActionEvent e){
            createInputFrame();
        }
    }
    class appendItemBtnListener implements ActionListener{
        void appendFile( String[] data, Boolean zipped )
                throws FileNotFoundException, IOException, ClassNotFoundException,
                KeyNotUniqueException {
            try ( Index idx = Index.load( idxname );
                  RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
                    Tourism book = Tourism.read( data );
                    if ( book == null ) return;
                    idx.test( book );
                    long pos = Buffer.writeObject( raf, book, zipped );
                    idx.put( book, pos );
            }
        }
        void appendFile( String[] data) throws IOException,
                ClassNotFoundException, KeyNotUniqueException {
            try ( Index idx = Index.load( idxname );
                RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
                Tourism book = Tourism.read( data );
                long pos = Buffer.writeObject( raf, book, false );
                if( pos != 0){
                    showMessageDialog(inputFrame, "Отлично! Перед выходом из программы сохраните данные!");
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e){
            try {
                String[] data = {
                        dateArea.getText(),
                        timeArea.getText(),
                        placeArea.getText(),
                        nameArea.getText(),
                        numberArea.getText(),
                        bagadgeNumberArea.getText(),
                        bagadgeWeightArea.getText(),
                };
                if (data[0].equals("") || data[1].equals("") || data[2].equals("")
                        || data[3].equals("") || data[4].equals("") || data[5].equals("") || data[6].equals("")){
                    showMessageDialog(inputFrame, "Все поля должны быть заполнены!");
                    return;
                }
                appendFile(data, false);
            } catch (IOException | ClassNotFoundException | KeyNotUniqueException ioException) {
                ioException.printStackTrace();
            }
            inputFrame.setVisible(false);
        }
    }

    //////////////////////////////// REMOVE RECORD METHODS ///////////////////////////////////////
    private static void deleteBackup() {
        new File( filenameBak ).delete();
        new File( idxnameBak ).delete();
    }
    class deleteAllListener implements ActionListener{
        void deleteFile() {
            deleteBackup();
            new File( filename ).delete();
            new File( idxname ).delete();
        }
        @Override
        public void actionPerformed(ActionEvent e){
            array.clear();
            deleteFile();
            textArea.setText(null);
        }
    }
    class deleteByIndexListener implements ActionListener{
        boolean deleteFile( long[] poss )
                throws ClassNotFoundException, IOException, KeyNotUniqueException {
            backup();
            Arrays.sort( poss );
            try ( Index idx = Index.load( idxname );
                  RandomAccessFile fileBak= new RandomAccessFile(filenameBak, "rw");
                  RandomAccessFile file = new RandomAccessFile( filename, "rw")) {
                boolean[] wasZipped = new boolean[] {false};
                long pos;
                while (( pos = fileBak.getFilePointer()) < fileBak.length() ) {
                    Tourism book = (Tourism)
                            Buffer.readObject( fileBak, pos, wasZipped );
                    if ( Arrays.binarySearch(poss, pos) < 0 ) { // if not found in deleted
                        long ptr = Buffer.writeObject( file, book, wasZipped[0] );
                        idx.put( book, ptr );
                    }
                }
            }
            return true;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            String value = removeArea.getText();
            if (value.equals("")){
                showMessageDialog(frame, "Please enter value");
                return;
            }
            try{
                textArea.setText(null);

                deleteFile(new long[]{Long.parseLong(value)});
            }catch (Exception ex){
                showMessageDialog(frame, ex.getMessage());
            }
        }
    }
    private static void backup() {
        deleteBackup();
        new File( filename ).renameTo( new File( filenameBak ));
        new File( idxname ).renameTo( new File( idxnameBak ));
    }

    //////////////////////////////// HELP BLOCK METHODS ///////////////////////////////////////
    class helpListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            showMessageDialog(inputFrame, "== AVIALINE MANAGER == \nРазработчик: Петров А.А., 14 группа, 2 курс \n@ФПМИ БГУ");
            return;
        }
    }

    //////////////////////////////// STYLE METHODS ///////////////////////////////////////
    class fontListener implements ActionListener{
        private void createInputFontFrame(){
            fontFrame = new JFrame("Введите данные");
            fontFrame.setVisible(true);
            fontFrame.setSize(80,120);
            JPanel inputPanel = new JPanel();

            JLabel numberLabel = new JLabel("Размер");
            dateArea = new JTextArea();
            dateArea.setColumns(15);

            JButton appendItemBtn = new JButton("Готово");
            appendItemBtn.addActionListener(new fontBtnListener());

            inputPanel.add(numberLabel);
            inputPanel.add(dateArea);

            inputPanel.add(appendItemBtn);

            fontFrame.getContentPane().add(BorderLayout.CENTER, inputPanel);
        }
        @Override
        public void actionPerformed(ActionEvent e)
        {
            createInputFontFrame();
        }
    }
    class fontBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String s = dateArea.getText();
            float size = Integer.parseInt(s);
            Font font = textArea.getFont();

            textArea.setFont( font.deriveFont(size) );
            fontFrame.setVisible(false);
        }
    }

    //////////////////////////////// COMMON METHODS ///////////////////////////////////////
    private void removeFile(){
        File file = new File(filename);
        file.delete();
    }
    private static IndexBase indexByArg( String arg, Index idx ) {
        IndexBase pidx = null;
        switch (arg) {
            case "number":
                pidx = idx.number;
                break;
            case "date":
                pidx = idx.date;
                break;
            case "time":
                pidx = idx.time;
                break;
            case "place":
                pidx = idx.place;
                break;
            case "weight":
                pidx = idx.weight;
                break;
            default:
                System.err.println("Invalid index specified: " + arg);
                break;
        }
        return pidx;
    }
    private void readArray(){
        try ( RandomAccessFile raf = new RandomAccessFile( filename, "rw" )){
            long pos;
            array = new ArrayList<>();
            while (( pos = raf.getFilePointer()) < raf.length() ){
                Tourism r = (Tourism) Buffer.readObject(raf, pos, new boolean[]{false});
                array.add(r);
            }
        }
        catch (Exception ex){ }
    }
    void saveAfterRemove(ArrayList<Tourism> array){
        try ( RandomAccessFile raf = new RandomAccessFile( filename, "rw" )){
            for(int i = 0; i < array.size(); i++){
                Buffer.writeObject( raf, array.get(i), false );
            }
        }
        catch (Exception ex){ }
    }
    class closeListener implements WindowListener{
        @Override
        public void windowOpened(WindowEvent e) { }
        @Override
        public void windowClosing(WindowEvent e)
        {
            removeFile();
            saveAfterRemove(array);
        }
        @Override
        public void windowClosed(WindowEvent e) { }
        @Override
        public void windowIconified(WindowEvent e) { }
        @Override
        public void windowDeiconified(WindowEvent e) { }
        @Override
        public void windowActivated(WindowEvent e) { }
        @Override
        public void windowDeactivated(WindowEvent e) { }
    }
    class quitListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }
}
