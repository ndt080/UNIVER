package mypack;
import java.io.*;

public class Connector {

    private String filename;

    public Connector( String filename )
    {
        this.filename = filename;
    }

    public void writeAb( Client[] b) throws IOException {
        FileOutputStream fos = new FileOutputStream (filename);
        try ( ObjectOutputStream oos = new ObjectOutputStream( fos )){
            oos.writeInt( b.length );
            for ( int i = 0; i < b.length; i++){
                oos.writeObject( b[i] );
            }
            oos.flush();
        }
    }

    public void writeAd( Designer a) throws IOException{
        FileOutputStream fos = new FileOutputStream (filename);
        try ( ObjectOutputStream oos = new ObjectOutputStream( fos )){
            oos.writeObject( a );
            oos.flush();
        }
    }

    public Client[] readAb() throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(filename);
        try ( ObjectInputStream oin = new ObjectInputStream(fis)){
            int length = oin.readInt();
            Client[] result = new Client[length];
            for ( int i = 0; i < length; i++ ){
                result[i] = (Client) oin.readObject();
            }
            return result;
        }
    }

    public Designer readAd() throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(filename);
        try ( ObjectInputStream oin = new ObjectInputStream(fis)){
            Designer result = (Designer) oin.readObject();
            return result;
        }
    }

}
