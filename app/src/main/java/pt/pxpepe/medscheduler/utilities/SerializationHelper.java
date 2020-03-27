package pt.pxpepe.medscheduler.utilities;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationHelper {

    public static byte[] serialize(Object object)  {
        byte[] bytes = new byte[0];
        
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            // transform object to stream and then to a byte array
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            objectOutputStream.close();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            Log.e("",e.toString());
        }

        return bytes;
    }

    public static Object deserialize(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream;
        Object exitObject = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            exitObject = objectInputStream.readObject();
        } catch (Exception e) {
            Log.e("",e.toString());
        }
        return exitObject;
    }

}
