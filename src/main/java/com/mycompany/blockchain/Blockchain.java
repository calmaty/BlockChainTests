/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.blockchain;

import java.nio.ByteBuffer;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 *
 * @author Christoffer
 */
public class Blockchain {
    
    ArrayList<Block> blocks;
    int id;

    public Blockchain() {
        id = 0;
        blocks = new ArrayList<Block>();
        Block block = new Block();
        blocks.add(block);
        block.Mine();
        
    }
    
    public String addBlock(ArrayList<Player> Players, int id)
    {
        //id++;
        String PreviousHash = blocks.get(blocks.size()-1).getHash();
        Block block = new Block(Players, id, PreviousHash);
        blocks.add(block);
        return block.Mine();
    }
    
    public void appendChain(Blockchain bc)
    {
       int agreementIndex = 0;
       
        int x;
        if(blocks.size() <= bc.blocks.size())
        {
           x = blocks.size() - 1;
        }
        else
        {
           x = bc.blocks.size() - 1;
           //UniversalBlocks.blocks.removeAll(blocks.subList(x + 1, blocks.size()));
           blocks.subList(x + 1, blocks.size()).clear();
           
        }
       
        for(int y = x; y >= 0 ; y--)
        {
            if(blocks.get(y).equals(bc.blocks.get(y)))
            {
                agreementIndex = y; 
                break;
            }
            else
            {
                //UniversalBlocks.blocks.remove(blocks.get(y));
                blocks.remove(y);
            }
        }
        
        for(int y = agreementIndex +1; y < bc.blocks.size() ; y++)
        {
            blocks.add(bc.blocks.get(y));
        }
        
    }
    
    public ArrayList<Block> appendChain(ArrayList<Person> activePeople)
    {
        ArrayList<Block> otherBlocks = new ArrayList<Block>();
        int agreementIndex = -1;
        int x = -1;
        for(Person p : activePeople)
        {
            if(x == -1)
            {
                x = p.blockchain.blocks.size() - 1;
            }
            else if(p.blockchain.blocks.size() - 1 < x)
            {
                x = p.blockchain.blocks.size() - 1;
            }
        }
        
        //System.out.println("smallest " +  x);
        for(Person p : activePeople)
        {
            otherBlocks.addAll(new ArrayList<Block>(p.blockchain.blocks.subList(x + 1, p.blockchain.blocks.size())));
            p.blockchain.clearFrom(x + 1);
        }
        
        for(int y = x; y >= 0 ; y--)
        {
            boolean agreement = true;
             for(Person p : activePeople)
            {
                 if(!blocks.get(y).equals(p.blockchain.blocks.get(y)))
                 {
                     agreement = false;
                 }
            }    
            
            if(agreement)
            {
                //System.out.println("agree at index " + y);
                agreementIndex = y; 
                break;
            }
            else
            {
                 for(Person p : activePeople)
                {
                     otherBlocks.add(p.blockchain.blocks.get(y));
                     p.blockchain.blocks.remove(y);
                }
            }
        }
        
        return otherBlocks;
       
    }
    
    public ArrayList<Block> appendChainSigned(ArrayList<Blockchain> bcs ,  Map<Integer, PublicKey> keys)
    {
        ArrayList<Block> otherBlocks = new ArrayList<Block>();
        //ArrayList<Integer> ids = new ArrayList<Integer>();
        int agreementIndex = -1;
        int x = -1;
        for(Blockchain bc : bcs)
        {
            if(x == -1)
            {
                x = bc.blocks.size() - 1;
            }
            else if(bc.blocks.size() - 1 < x)
            {
                x = bc.blocks.size() - 1;
            }
        }
        
        System.out.println("smallest " +  x);
        for(Blockchain bc : bcs)
        {
            //otherBlocks.addAll(new ArrayList<Block>(bc.blocks.subList(x + 1, bc.blocks.size())));
            ArrayList<Block> Blocks = new ArrayList<Block>(bc.blocks.subList(x + 1, bc.blocks.size()));
            for(Block b : blocks)
            {
                b.ResetNounce();
            }
            
            otherBlocks.removeAll(Blocks);
//            for(Block b : Blocks)
//            {
//                //UniversalBlocks.UniversalBlocks.remove((Integer.valueOf(b.id)));
//            }
            otherBlocks.addAll(Blocks);
           
        }
        
        blocks.subList(x + 1, blocks.size()).clear();
        
        
        
        for(int y = x; y >= 0 ; y--)
        {
            boolean agreement = true;
            for(Blockchain bc : bcs)
            {
                 if(!blocks.get(y).equals(bc.blocks.get(y)))
                 {
                     agreement = false;
                 }
            }    
            
            if(agreement)
            {
                System.out.println("agree at index " + y);
                agreementIndex = y; 
                break;
            }
            else
            {
                blocks.get(y).ResetNounce();
                if(!otherBlocks.contains(blocks.get(y)))
                { 
                     otherBlocks.add(blocks.get(y));
                }
                //otherBlocks.add(blocks.get(y));
                blocks.remove(y);
                //UniversalBlocks.UniversalBlocks.remove(Integer.valueOf(blocks.get(y).id));
            }
        }
        
       for(Block b : otherBlocks)
       {
           b.ResetNounce();
       }
        
        return otherBlocks;
        // Fix her <>
//        for (Block b : otherBlocks)
//        {
//            if (!ids.contains(b.id))
//            {
//                try
//                {
//                    Signature sig = Signature.getInstance("SHA1withRSA");
//
//                    boolean allVerified = true;
//                    byte[] blockBytes = ByteBuffer.allocate(4).putInt(b.id).array();
//                    for(Player pl : b.Players)
//                    {
//                        if(keys.containsKey(pl.id))
//                        {
//                            PublicKey key = keys.get(pl.id);
//
//                            sig.initVerify(key);
//
//                            sig.update(blockBytes);
//
//                            if(!sig.verify(pl.signature))
//                            {
//                                allVerified = false;
//                            }
//                        }
//                        else
//                        {
//                            allVerified = false;
//                        }
//                    }
//
//                    if(allVerified)
//                    {
//                        ids.add(b.id);
//                        addBlock(b.Players, b.id);
//                        System.out.println("Accepted");
//                    }
//                    else
//                    {
//                        System.out.println("Rejected");
//                    }
//                }
//                catch (Exception e)
//                {
//                    System.err.println("Caught exception " + e.toString());
//                }
//
//                
//            }
//        }
    }
    
    public void addBlocks(ArrayList<Block> blocks)
    {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        Collections.sort(blocks);
        for (Block b : blocks)
        {
            if (!ids.contains(b.id))
            {
                ids.add(b.id);
                addBlock(b.Players, b.id);
                //System.out.println("add " + b.id);
            }
        }
    }
    
    public void clearFrom(int index)
    {
        blocks.subList(index, blocks.size()).clear();
    }
   
    
    public boolean verifyChain()
    {
        boolean verifyed = true;
        
        for(int i = blocks.size() -1 ; i > 0 ; i--)
        {
            String hash = blocks.get(i).getHash();
            String first3 = hash.substring(0, Math.min(hash.length(), 3));
            String previousHash = blocks.get(i).PreviousHash;
            String previousBlockHash = blocks.get(i-1).getHash();
            
            if(!previousHash.equals(previousBlockHash) || !first3.equals("000"))
            {
                verifyed = false;
            }
                
        }
        
        return verifyed;
    }
}
