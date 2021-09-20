package mypack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

import static javax.swing.JOptionPane.showMessageDialog;

public class SimpleGUI
{
    private JFrame frame, inputFrame, fontFrame;
    private JTextArea textArea = new JTextArea();
    private JTextArea clientChoice, numberArea, sArea, floorArea, roomsArea, adressArea, typeArea, periodArea;

    static final String filename = "Rooms.dat";
    static ArrayList<Room> array;
    public void go()
    {
        frame = new JFrame("Lab 9");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new closeListener());
        JLabel clientLabel = new JLabel("Введите значение ключа для поиска");
        clientChoice = new JTextArea();
        clientChoice.setRows(1);
        clientChoice.setColumns(20);

        JPanel clientChoicePanel = new JPanel();
        clientChoicePanel.add(clientLabel);
        clientChoicePanel.add(clientChoice);

        JMenuBar menuBar = new JMenuBar();
        //////////////////////////////////////////////////////////////////////////////////
        JMenu printMenu = new JMenu("Вывод");
        //////////////////////////////////////////////////////////////////////////////////
        JMenu printAllRecords = new JMenu("Вывести все записи");
        /////////////////////////////////////////////////////////////////////////////////
        JMenuItem printUnsorted = new JMenuItem("Без сортировки");
        printUnsorted.addActionListener(new printDefaultListener());

        JMenu printSorted = new JMenu("Сортировать");
        JMenuItem printSortedByNumber = new JMenuItem("По номеру");
        printSortedByNumber.addActionListener(new printSortedByNumberListener());
        JMenuItem printSortedByS = new JMenuItem("По площади");
        printSortedByS.addActionListener(new printSortedBySListener());
        JMenuItem printSortedByFloor = new JMenuItem("По этажу");
        printSortedByFloor.addActionListener(new printSortedByFloorListener());
        JMenuItem printSortedByPeriod = new JMenuItem("По сроку эксплуатации");
        printSortedByPeriod.addActionListener(new printSortedByPeriodListener());

        printSorted.add(printSortedByNumber);
        printSorted.add(printSortedByS);
        printSorted.add(printSortedByFloor);
        printSorted.add(printSortedByPeriod);
        printAllRecords.add(printSorted);
        printAllRecords.add(printUnsorted);
        ///////////////////////////////////////////////////////////////////////////////////

        JMenu Search = new JMenu("Поиск");

        JMenuItem searchByNumber = new JMenuItem("По номеру");
        searchByNumber.addActionListener(new searchByNumberListener());
        JMenuItem searchByS = new JMenuItem("По площади");
        searchByS.addActionListener(new searchBySListener());
        JMenuItem searchByFloor = new JMenuItem("По этажу");
        searchByFloor.addActionListener(new searchByFloorListener());
        JMenuItem searchByPeriod = new JMenuItem("По сроку эксплуатации");
        searchByPeriod.addActionListener(new searchByPeriodListener());

        Search.add(searchByNumber);
        Search.add(searchByS);
        Search.add(searchByFloor);
        Search.add(searchByPeriod);
        printMenu.add(printAllRecords);
        printMenu.add(Search);

        JMenu addMenu = new JMenu("Добавить");
        JMenuItem addNew = new JMenuItem("Добавить новую запись");
        addNew.addActionListener(new appendItemListener());
        addMenu.add(addNew);

        JMenu deleteMenu = new JMenu("Удалить запись");

        JMenuItem deleteByFirm = new JMenuItem("Удалить по индексу");
        deleteByFirm.addActionListener(new deleteByIndexListener());
        JMenuItem deleteAll = new JMenuItem("Удалить все записи");
        deleteAll.addActionListener(new deleteAllListener());

        deleteMenu.add(deleteByFirm);
        deleteMenu.add(deleteAll);

        JMenu fileMenu = new JMenu("File");
        JMenuItem quit = new JMenuItem("Quit");
        fileMenu.add(quit);
        quit.addActionListener(new quitListener());
        menuBar.add(fileMenu);

        menuBar.add(addMenu);
        menuBar.add(printMenu);
        menuBar.add(deleteMenu);

        JMenu viewMenu = new JMenu("View");
        JMenuItem font = new JMenuItem("Font");
        viewMenu.add(font);
        font.addActionListener(new fontListener());
        menuBar.add(viewMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        helpMenu.add(about);
        about.addActionListener(new helpListener());
        menuBar.add(helpMenu);

        JScrollPane scroller = new JScrollPane(textArea);
        textArea.setEditable(false);

        frame.getContentPane().add(BorderLayout.CENTER, scroller);
        frame.getContentPane().add(BorderLayout.NORTH, clientChoicePanel);

        frame.setLocation(300, 300);
        frame.setJMenuBar(menuBar);
        frame.setSize(600,600);
        frame.setVisible(true);

        readArray();
    }
    private void readArray() //done
    {
        try ( RandomAccessFile raf = new RandomAccessFile( filename, "rw" ))
        {
            long pos;
            array = new ArrayList();
            while (( pos = raf.getFilePointer()) < raf.length() )
            {
                Room r = (Room) Buffer.readObject( raf, pos );
                array.add(r);
            }
        }
        catch (Exception ex)
        {

        }
    }

    private void removeFile() //done
    {
        File file = new File(filename);
        file.delete();
    }

    void saveAfterRemove(ArrayList<Room> array) //done
    {
        try ( RandomAccessFile raf = new RandomAccessFile( filename, "rw" ))
        {
            for(int i = 0; i < array.size(); i++)
            {
                Buffer.writeObject( raf, array.get(i) );
            }
        }
        catch (Exception ex){
        }
    }

    class closeListener implements WindowListener //done
    {
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

    private void createInputFrame() //done
    {
        inputFrame = new JFrame("Введите данные");
        inputFrame.setVisible(true);
        inputFrame.setSize(80,450);
        JPanel inputPanel = new JPanel();

        JLabel numberLabel = new JLabel("Номер");
        numberArea = new JTextArea();
        numberArea.setColumns(15);

        JLabel sLabel = new JLabel("Площадь");
        sArea = new JTextArea();
        sArea.setColumns(15);

        JLabel floorLabel = new JLabel("Этаж");
        floorArea = new JTextArea();
        floorArea.setColumns(15);

        JLabel roomsLabel = new JLabel("Кол-во комнат");
        roomsArea = new JTextArea();
        roomsArea.setColumns(15);

        JLabel adressLabel = new JLabel("Адрес");
        adressArea = new JTextArea();
        adressArea.setColumns(15);

        JLabel typeLabel = new JLabel("Тип здания");
        typeArea = new JTextArea();
        typeArea.setColumns(15);

        JLabel periodLabel = new JLabel("Срок эксплуатации");
        periodArea = new JTextArea();
        periodArea.setColumns(15);

        JButton appendItemBtn = new JButton("Добавить запись");
        appendItemBtn.addActionListener(new appendItemBtnListener());
        inputPanel.add(numberLabel);
        inputPanel.add(numberArea);

        inputPanel.add(sLabel);
        inputPanel.add(sArea);

        inputPanel.add(floorLabel);
        inputPanel.add(floorArea);

        inputPanel.add(roomsLabel);
        inputPanel.add(roomsArea);

        inputPanel.add(adressLabel);
        inputPanel.add(adressArea);

        inputPanel.add(typeLabel);
        inputPanel.add(typeArea);

        inputPanel.add(periodLabel);
        inputPanel.add(periodArea);

        inputPanel.add(appendItemBtn);

        inputFrame.getContentPane().add(BorderLayout.CENTER, inputPanel);
    }

    class printDefaultListener implements ActionListener //done
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            textArea.setText(null);
            for(int i = 0; i < array.size(); i++)
            {
                textArea.append(array.get(i).toString() + "\n");
            }
        }
    }

    class printSortedByNumberListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String value = clientChoice.getText();
            try
            {
                if (value.compareTo("reverse") == 0)
                    array.sort(Comparator.comparingInt(a -> Integer.parseInt(a.Number)));
                else
                    array.sort(Comparator.comparingInt(a -> -Integer.parseInt(a.Number)));

                textArea.setText(null);
                for(int i = 0; i < array.size(); i++)
                {
                    textArea.append(array.get(i).toString() + "\n");
                }
            }
            catch (Exception ex)
            {
                showMessageDialog(frame, ex.getMessage());
            }
        }
    }

    class printSortedBySListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String value = clientChoice.getText();
            try
            {
                if (value.compareTo("reverse") == 0)
                    array.sort(Comparator.comparingInt(a -> Integer.parseInt(a.S)));
                else
                    array.sort(Comparator.comparingInt(a -> -Integer.parseInt(a.S)));

                textArea.setText(null);
                for(int i = 0; i < array.size(); i++)
                {
                    textArea.append(array.get(i).toString() + "\n");
                }
            }
            catch (Exception ex)
            {
                showMessageDialog(frame, ex.getMessage());
            }
        }
    }

    class printSortedByPeriodListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String value = clientChoice.getText();
            try
            {
                if (value.compareTo("reverse") == 0)
                    array.sort(Comparator.comparingInt(a -> Integer.parseInt(a.Period)));
                else
                    array.sort(Comparator.comparingInt(a -> -Integer.parseInt(a.Period)));

                textArea.setText(null);
                for(int i = 0; i < array.size(); i++)
                {
                    textArea.append(array.get(i).toString() + "\n");
                }
            }
            catch (Exception ex)
            {
                showMessageDialog(frame, ex.getMessage());
            }
        }
    }

    class printSortedByFloorListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String value = clientChoice.getText();
            try
            {
                if (value.compareTo("reverse") == 0)
                    array.sort(Comparator.comparingInt(a -> Integer.parseInt(a.Floor)));
                else
                    array.sort(Comparator.comparingInt(a -> -Integer.parseInt(a.Floor)));

                textArea.setText(null);
                for(int i = 0; i < array.size(); i++)
                {
                    textArea.append(array.get(i).toString() + "\n");
                }
            }
            catch (Exception ex)
            {
                showMessageDialog(frame, ex.getMessage());
            }
        }
    }

    class searchByNumberListener implements ActionListener //done
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String value = clientChoice.getText();
            textArea.setText(null);
            if (value.equals(""))
            {
                showMessageDialog(frame, "Please enter value");
                return;
            }

            for(int i = array.size()-1; i >= 0; i--)
            {
                if( value.compareTo(array.get(i).Number) == 0)
                {
                    textArea.append(array.get(i).toString() + "\n");
                }
            }
        }
    }

    class searchByFloorListener implements ActionListener //done
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String value = clientChoice.getText();
            textArea.setText(null);
            if (value.equals(""))
            {
                showMessageDialog(frame, "Please enter value");
                return;
            }

            for(int i = array.size()-1; i >= 0; i--)
            {
                if( value.compareTo(array.get(i).Floor) == 0)
                {
                    textArea.append(array.get(i).toString() + "\n");
                }
            }
        }
    }

    class searchByPeriodListener implements ActionListener //done
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String value = clientChoice.getText();
            textArea.setText(null);
            if (value.equals(""))
            {
                showMessageDialog(frame, "Please enter value");
                return;
            }

            for(int i = array.size()-1; i >= 0; i--)
            {
                if( value.compareTo(array.get(i).Period) == 0)
                {
                    textArea.append(array.get(i).toString() + "\n");
                }
            }
        }
    }

    class searchBySListener implements ActionListener //done
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String value = clientChoice.getText();
            textArea.setText(null);
            if (value.equals(""))
            {
                showMessageDialog(frame, "Please enter value");
                return;
            }

            for(int i = array.size()-1; i >= 0; i--)
            {
                if( value.compareTo(array.get(i).S) == 0)
                {
                    textArea.append(array.get(i).toString() + "\n");
                }
            }
        }
    }

    class deleteAllListener implements ActionListener //done
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            array.clear();
            textArea.setText("");
        }
    }

    class deleteByIndexListener implements ActionListener //delete by index
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String value = clientChoice.getText();
            if (value.equals(""))
            {
                showMessageDialog(frame, "Please enter value");
                return;
            }
            try
            {
                int ind = Integer.parseInt(value);
                textArea.setText(null);

                array.remove(ind);
            }
            catch (Exception ex)
            {
                showMessageDialog(frame, ex.getMessage());
            }
        }
    }

    class appendItemListener implements ActionListener //done
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            createInputFrame();
            //inputFrame.setVisible(true);
        }
    }

    class appendItemBtnListener implements ActionListener //done
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String number, s, floor, rooms, adress, type, period;
            number = numberArea.getText();
            s = sArea.getText();
            floor = floorArea.getText();
            rooms = roomsArea.getText();
            adress = adressArea.getText();
            type = typeArea.getText();
            period = periodArea.getText();
            if (number.equals("") || s.equals("") || floor.equals("") || rooms.equals("") || adress.equals("") || type.equals("") || period.equals(""))
            {
                showMessageDialog(inputFrame, "Все поля должны быть заполнены!");
                return;
            }
            try ( RandomAccessFile raf = new RandomAccessFile( filename, "rw" ))
            {
/*                Tourism newTourism = new Tourism();
                newTourism.dateRace = date;
                newTourism.timeRace = timeRace;
                newTourism.placeComing = placeComing;
                newTourism.namePassenger = namePassenger;
                newTourism.numberRace = numberRace;
                newTourism.bagadgeNumbers = bagadgeNumbers;
                newTourism.bagadgeWeight = bagadgeWeight;
                */
                Room newRoom = new Room();
                newRoom.Number = number;
                newRoom.S = s;
                newRoom.Floor = floor;
                newRoom.Period = period;
                newRoom.RooomsAmount = rooms;
                newRoom.Street = adress;
                newRoom.Type = type;
                array.add(newRoom);
            }
            catch (Exception ex)
            {
            }
            inputFrame.setVisible(false);
        }
    }

    class quitListener implements ActionListener //done
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    }

    class helpListener implements ActionListener //done
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            showMessageDialog(inputFrame, " Подготовил Щербенок Андрей Николаевич, 14 группа, 2 курс, ФПМИ, БГУ");
            return;
        }
    }

    class fontListener implements ActionListener //done
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            createInputFontFrame();
        }
    }

    private void createInputFontFrame() //done
    {
        fontFrame = new JFrame("Введите данные");
        fontFrame.setVisible(true);
        fontFrame.setSize(80,120);
        JPanel inputPanel = new JPanel();

        JLabel numberLabel = new JLabel("Размер");
        numberArea = new JTextArea();
        numberArea.setColumns(15);

        JButton appendItemBtn = new JButton("Готово");
        appendItemBtn.addActionListener(new fontBtnListener());

        inputPanel.add(numberLabel);
        inputPanel.add(numberArea);

        inputPanel.add(appendItemBtn);

        fontFrame.getContentPane().add(BorderLayout.CENTER, inputPanel);
    }

    class fontBtnListener implements ActionListener //done
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String s = numberArea.getText();
            float size = Integer.parseInt(s);
            Font font = textArea.getFont();

            textArea.setFont( font.deriveFont(size) );
            fontFrame.setVisible(false);
        }
    }
}
