/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romppu.tutorial.chat.common;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author roman
 */
public class ImageData implements Serializable {

    private byte[] imageBytes;

    public ImageData() {
    }

    /**
     * @return the imageBytes
     */
    public byte[] getImageBytes() {
        return imageBytes;
    }

    /**
     * @param imageBytes the imageBytes to set
     */
    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public static ImageData createInstance(File file) throws IOException {
        ImageData data = new ImageData();
        data.setImageBytes(Tools.getFileBytes(file));
        return data;
    }

    /*
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.write(imageBytes);
    }

    private void readObject(ObjectInputStream stream) throws IOException,
            ClassNotFoundException {
        stream.defaultReadObject();
        stream.r
    }*/
}
