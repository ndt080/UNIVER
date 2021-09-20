package tourism;

import java.io.*;
import java.util.*;
import java.util.zip.*;

class KeyComp implements Comparator<String> {
    public int compare(String o1, String o2) {
        // right order:
        return o1.compareTo(o2);
    }
}

class KeyCompReverse implements Comparator<String> {
    public int compare(String o1, String o2) {
        // reverse order:
        return o2.compareTo(o1);
    }
}

interface IndexBase {
    String[] getKeys( Comparator<String> comp );
    void put( String key, long value );
    boolean contains( String key );
    long[] get( String key );
}

class IndexOne2One implements Serializable, IndexBase {
    // Unique keys
    // class release version:
    private static final long serialVersionUID = 1L;

    private final TreeMap<String,Long> map;

    public IndexOne2One() {
        map = new TreeMap<String,Long> ();
    }

    public String[] getKeys( Comparator<String> comp ) {
        String[] result = map.keySet().toArray( new String[0] );
        Arrays.sort( result, comp );
        return result;
    }

    public void put( String key, long value ) {
        map.put(key, value);
    }

    public boolean contains( String key ) {
        return map.containsKey(key);
    }

    public long[] get( String key ) {
        long pos = map.get(key);
        return new long[] {pos};
    }
}

class IndexOne2N implements Serializable, IndexBase {
    // Not unique keys
    // class release version:
    private static final long serialVersionUID = 1L;

    private final TreeMap<String,long[]> map;

    public IndexOne2N() {
        map = new TreeMap<String,long[]> ();
    }

    public String[] getKeys( Comparator<String> comp ) {
        String[] result = map.keySet().toArray( new String[0] );
        Arrays.sort( result, comp );
        return result;
    }

    public void put( String key, long value ) {
        long[] arr = map.get(key);
        arr = ( arr != null ) ?
                Index.InsertValue( arr, value ) :
                new long[] {value};
        map.put(key, arr);
    }

    public void put( String keys,   // few keys in one string
                     String keyDel, // key delimiter
                     long value ) {
        StringTokenizer st = new StringTokenizer( keys, keyDel );
        int num = st.countTokens();
        for ( int i= 0; i < num; i++ ) {
            String key = st.nextToken();
            key = key.trim();
            put( key, value );
        }
    }

    public boolean contains( String key ) {
        return map.containsKey(key);
    }

    public long[] get( String key ) {
        return map.get( key );
    }
}

class KeyNotUniqueException extends Exception {
    // class release version:
    private static final long serialVersionUID = 1L;

    public KeyNotUniqueException( String key ) {
        super( new String( "Key is not unique: " + key ));
    }
}

public class Index implements Serializable, Closeable {
    // class release version:
    private static final long serialVersionUID = 1L;

    public static long[] InsertValue( long[] arr, long value ) {
        int length = ( arr == null ) ? 0 : arr.length;
        long [] result = new long[length + 1];
        System.arraycopy(arr, 0, result, 0, length);
        result[length] = value;
        return result;
    }

    IndexOne2One          number;
    IndexOne2One          date;
    IndexOne2One          time;
    IndexOne2One          place;
    IndexOne2N            weight;

    public void test( Tourism book ) throws KeyNotUniqueException {
        assert( book != null );
        if ( number.contains(book.numberRace)) {
            throw new KeyNotUniqueException( book.numberRace );
        }
        if ( date.contains( book.dateRace )) {
            throw new KeyNotUniqueException( book.dateRace );
        }
        if ( time.contains( book.timeRace )) {
            throw new KeyNotUniqueException( book.timeRace );
        }
        if ( place.contains( book.placeComing )) {
            throw new KeyNotUniqueException( book.placeComing );
        }
    }

    public void put(Tourism book, long value ) throws KeyNotUniqueException {
        test( book );
        number.put( book.numberRace, value );
        // place.put( book.namePassenger, Tourism.authorDel, value);
        date.put( book.dateRace, value);
        time.put( book.timeRace, value);
        place.put( book.placeComing, value);
        weight.put( book.bagadgeWeight, Tourism.authorDel, value);


    }

    public Index()  {
        number = new IndexOne2One();
        date   = new IndexOne2One();
        time   = new IndexOne2One();
        place  = new IndexOne2One();
        weight = new IndexOne2N();

    }

    public static Index load( String name )
            throws IOException, ClassNotFoundException {
        Index obj = null;
        try {
            FileInputStream file = new FileInputStream( name );
            try( ZipInputStream zis = new ZipInputStream( file )) {
                ZipEntry zen = zis.getNextEntry();
                if (!zen.getName().equals(Buffer.zipEntryName)) {
                    throw new IOException("Invalid block format");
                }
                try ( ObjectInputStream ois = new ObjectInputStream( zis )) {
                    obj = (Index) ois.readObject();
                }
            }
        } catch ( FileNotFoundException e ) {
            obj = new Index();
        }
        if ( obj != null ) {
            obj.save( name );
        }
        return obj;
    }

    private transient String filename = null;

    public void save( String name ) {
        filename = name;
    }

    public void saveAs( String name ) throws IOException {
        FileOutputStream file = new FileOutputStream( name );
        try ( ZipOutputStream zos = new ZipOutputStream( file )) {
            zos.putNextEntry( new ZipEntry( Buffer.zipEntryName ));
            zos.setLevel( ZipOutputStream.DEFLATED );
            try ( ObjectOutputStream oos = new ObjectOutputStream( zos )) {
                oos.writeObject( this );
                oos.flush();
                zos.closeEntry();
                zos.flush();
            }
        }
    }

    public void close() throws IOException {
        saveAs( filename );
    }
}
