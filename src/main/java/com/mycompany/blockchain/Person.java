/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.blockchain;

import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Christoffer
 */
public class Person implements Comparable<Person> {
    
    String name;
    public int id;
    Blockchain blockchain;
    int AgrementSize;
    int ChainSize;
    int VerifiedBlocks;
    double PercentVerified;
    
    ArrayList<Integer> votes;
    ArrayList<Integer> ids = new ArrayList<Integer>();
    
    Map<Integer, PublicKey> PublicKeys = new HashMap<Integer, PublicKey>();
    
    Map<Integer, Integer> PublicKeyCounts = new HashMap<Integer, Integer>();
    
    Map<String, Block> AcceptedBlocks = new HashMap<String, Block>();
     
    Map<String, Block> PendingBlocks = new HashMap<String, Block>();
    
    ArrayList<Block> Vblocks = new ArrayList<Block>();
    ArrayList<Block> Qblocks = new ArrayList<Block>();
    
    PrivateKey privateKey;
    PublicKey publicKey;    
    byte[] signature;

    public Person(int id) {
        blockchain = new Blockchain();
        this.id = id;
        this.PercentVerified = 0;
        this.VerifiedBlocks = 0;
      
    }
    
    public void GenerateKeys()
    {
        try
        {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1204);
           
            
            KeyPair pair = keyGen.genKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();
          
            
//            Signature sig = Signature.getInstance("SHA1withRSA");
//            
//            byte[] bytes = ByteBuffer.allocate(4).putInt(id).array();
//            sig.initSign(privateKey);
//            sig.update(bytes);
//            
//            signature = sig.sign();
//            
//            sig.initVerify(publicKey);
//            
//            sig.update(bytes);
//            
//            return sig.verify(signature);
        }
        catch (Exception e)
        {
            System.err.println("Caught exception " + e.toString());
        }
    }
    
    public byte[] GenerateSignature(int id)
    {
        try
        {
            Signature sig = Signature.getInstance("SHA1withRSA");
            
            byte[] bytes = ByteBuffer.allocate(4).putInt(id).array();
            sig.initSign(privateKey);
            sig.update(bytes);
            
            return sig.sign();
        }
        catch (Exception e)
        {
            System.err.println("Caught exception " + e.toString());
            return new byte[0];
        
        }
    }
    
    public void vote(int vote)
    {
        votes.add(vote);
    }

    @Override
    public int compareTo(Person o) {
         return Comparator.comparing(Person::getAgrementSize)
              .thenComparing(Person::getPercentVerified)
              .thenComparing(Person::getChainSize)
              .thenComparingInt(Person::getId)
              .compare(this, o);
    }   
    
     @Override
    public boolean equals(Object o) {

        if (o == null) return false;
        if (o == this) return true; //if both pointing towards same object on heap

        Person a = (Person) o;
        return this.id == a.id;

    }

    public int getId() {
        return id;
    }

    public int getAgrementSize() {
        return AgrementSize;
    }

    public int getChainSize() {
        return ChainSize;
    }

    public int getVerifiedBlocks() {
        return VerifiedBlocks;
    }

    public double getPercentVerified() {
        return PercentVerified;
    }
    
    
}
