/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.blockchain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Christoffer
 */
public class Block implements Comparable<Block> {
    
    ArrayList<Player> Players;
    int nounce;
    int id;
    String PreviousHash;

    public Block(ArrayList<Player> Players, int id, String PreviousHash) {
        this.Players = Players;
        this.id = id;
        this.PreviousHash = PreviousHash;
        this.nounce = 0;
    }
    
      public Block(ArrayList<Player> Players, int id) {
        this.Players = Players;
        this.id = id;
        this.PreviousHash = "0";
        this.nounce = 0;
    }

    public Block() {
        this.PreviousHash = "0";
        this.id = 0;
    }
    
    public String Mine()
    {
        String hash;
        while(true)
        {
            hash = getHash();
            //not First 3
            String first3 = hash.substring(0, Math.min(hash.length(), 1));
            
            if(first3.equals("0"))
            {
                return hash;
            }
            else
            {
                nounce ++;
            }
        }
    }
    
    public void ResetNounce()
    {
        nounce = 0;
    }
    
    public boolean verifyBlock()
    {
        String hash = getHash();
        String first3 = hash.substring(0, Math.min(hash.length(), 3));
        
        return first3.equals("000");
    }
    
    public String getHash()
    {
        try
        {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos  = new ObjectOutputStream(bos);
        oos.writeObject(Players);
        byte[] bytes = bos.toByteArray();
        byte [] bytes2 = ByteBuffer.allocate(4).putInt(nounce).array();
        byte [] bytes3 = ByteBuffer.allocate(4).putInt(id).array();
        
        md.update(bytes);
        md.update(bytes2);
        md.update(bytes3);
        md.update(PreviousHash.getBytes());
        
        return bytesToHex(md.digest());
        }
        catch (Exception ex)
        {
            System.out.print("hello");
            return "000";
        }
    }

    private String bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
    
   @Override
   public boolean equals(Object obj) {
       
       Block otherBlock = (Block) obj;
       return this.getHash().equals(otherBlock.getHash());
   }

    @Override
    public int compareTo(Block o) {
         return id - o.id;
    }
    
}
