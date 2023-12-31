package com.fooddelivery.backend.Utils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.springframework.stereotype.Component;

import com.fooddelivery.backend.Exception.BadResultException;

@Component
public class ImageUtil {
    public byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.setLevel(deflater.BEST_COMPRESSION);
        deflater.finish();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] tmp = new byte[4 * 1024];

        try {
            while(!deflater.finished()) {
                int count = deflater.deflate(tmp);
                byteArrayOutputStream.write(tmp, 0, count);
            }
            byteArrayOutputStream.close();
        } catch (IOException ex) {
            throw new BadResultException(ex.getMessage());
        }
        return byteArrayOutputStream.toByteArray();

    }

    public byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        inflater.finished();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] tmp = new byte[4 * 1024];

       try {
        while(!inflater.finished()) {
            int count = inflater.inflate(tmp);
            byteArrayOutputStream.write(tmp, 0, count);
        }
       } catch (DataFormatException ex) {
        throw new BadResultException(ex.getMessage());
       }
       return byteArrayOutputStream.toByteArray();
    }
}
