/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.blockchain;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Christoffer
 */
public class Algorithems {
    
    public void StandartBlockChain(int population, int gamesPlayed, int minGameSize, int maxGameSize, int iterations)
    {
        long start = System.currentTimeMillis();
        double averageAverageGames = 0;
        double averageMostGames = 0;
        double averageLastGames = 0;
        double averageRepresentetGames = 0;
        
        for(int it = 0; it < iterations ; it++)
        {
            long start2 = System.currentTimeMillis();
            ArrayList<Person> persons = new ArrayList<Person>();
            for(int i = 0; i < population ; i++)
            {
                persons.add(new Person(i));
            }

            for(int i = 0; i < gamesPlayed ; i++)
            {
                ArrayList<Person> activePeople = new ArrayList<Person>();
                int nr;
                Random r = new Random();
                if(maxGameSize == minGameSize)
                {
                    nr = maxGameSize;
                }
                else
                {
                    nr = r.nextInt(maxGameSize-minGameSize) + minGameSize;
                }
                while(activePeople.size() < nr)
                {
                     int id = r.nextInt(population);
                     if(!activePeople.contains(persons.get(id)))
                     {
                         activePeople.add(persons.get(id)); 
                     }

                }

                ArrayList<Integer> Votes = new ArrayList<Integer>();

                for(Person p : activePeople)
                {
                    int LongestSize = 0;
                    ArrayList<Integer> agreementSize = new ArrayList<Integer>();
                    ArrayList<Integer> listSize = new ArrayList<Integer>();

                    for(Person p2 : activePeople)
                    {
                        if(p.id != p2.id && p.blockchain.verifyChain())
                        {
                            listSize.add(p2.blockchain.blocks.size());
                            p2.ChainSize = p2.blockchain.blocks.size();
                            if(p2.blockchain.blocks.size() > LongestSize)
                            {
                                LongestSize = p2.blockchain.blocks.size();
                            }

                            int x;
                            if(p.blockchain.blocks.size() <= p2.blockchain.blocks.size())
                            {
                               x = p.blockchain.blocks.size() - 1;
                            }
                            else
                            {
                               x = p2.blockchain.blocks.size() - 1;
                            }

                        }
                        else
                        {
                            agreementSize.add(0);
                            p2.AgrementSize = 0;
                            listSize.add(0);
                            p2.ChainSize = 0;
                        }
                    }

                      ArrayList<Person> Candidates = new ArrayList<Person>(activePeople);

                      Collections.sort(Candidates);

                      Votes.add(Candidates.get(Candidates.size() - 1).id);

                }

                int Winnerid = getPopularElement(Votes);
                ArrayList<Player> players = new ArrayList<Player>();


                 for(Person p : activePeople)
                 {
                     players.add(new Player(p.id));

                     p.blockchain.appendChain(persons.get(Winnerid).blockchain);
                 }
                 for(Person p : activePeople)
                 {
                      p.blockchain.addBlock(players, i + 1);
                 }
                 
                 if(i == gamesPlayed - 1)
                 {
                     System.out.println(activePeople.get(0).blockchain.blocks.size());
                     averageLastGames += activePeople.get(0).blockchain.blocks.size();
                 }
            }

            double averageGames = 0;
            double mostGames = 0;
            double RepresentetGames = 0;
            ArrayList<Block> blocks = new ArrayList<Block>();
            
            for(Person p : persons)
            {
                averageGames += p.blockchain.blocks.size();

                if(p.blockchain.blocks.size() > mostGames)
                {
                    mostGames = p.blockchain.blocks.size();
                }

                for(Block b : p.blockchain.blocks)
                {
                    if(!blocks.contains(b))
                    {
                        blocks.add(b);
                    }
                }
            }
            averageGames = averageGames/persons.size();
            RepresentetGames = blocks.size();
            
            averageAverageGames += averageGames;
            averageMostGames += mostGames;
            averageRepresentetGames += RepresentetGames;
            
            
            System.out.println(averageGames);
            System.out.println(mostGames);
            System.out.println(RepresentetGames);
            System.out.print(System.currentTimeMillis() - start2);
            System.out.println("---");
        }
        
         averageAverageGames = averageAverageGames/iterations;
         averageMostGames = averageMostGames/iterations;
         averageLastGames = averageLastGames/iterations;
         averageRepresentetGames = averageRepresentetGames/iterations;
         
          System.out.println("===============");
          System.out.println(averageLastGames);
          System.out.println(averageAverageGames);
          System.out.println(averageMostGames);
          System.out.println(averageRepresentetGames);
          System.out.print((System.currentTimeMillis() - start) /iterations);
          
          
    }
    
    public void MajorityRules(int population, int gamesPlayed, int minGameSize, int maxGameSize, int iterations)
    {
        long start = System.currentTimeMillis();
        double averageAverageGames = 0;
        double averageMostGames = 0;
        double averageLastGames = 0;
        double averageRepresentetGames = 0;
        
        for(int it = 0; it < iterations ; it++)
        {
            long start2 = System.currentTimeMillis();
            ArrayList<Person> persons = new ArrayList<Person>();
            for(int i = 0; i < population ; i++)
            {
                persons.add(new Person(i));
            }

            for(int i = 0; i < gamesPlayed ; i++)
            {
                ArrayList<Person> activePeople = new ArrayList<Person>();
                int nr;
                Random r = new Random();
                if(maxGameSize == minGameSize)
                {
                    nr = maxGameSize;
                }
                else
                {
                    nr = r.nextInt(maxGameSize-minGameSize) + minGameSize;
                }
                while(activePeople.size() < nr)
                {
                     int id = r.nextInt(population);
                     if(!activePeople.contains(persons.get(id)))
                     {
                         activePeople.add(persons.get(id)); 
                     }

                }

                ArrayList<Integer> Votes = new ArrayList<Integer>();

                for(Person p : activePeople)
                {
                    //System.out.println(p.id);
                    int LongestSize = 0;
                    ArrayList<Integer> agreementSize = new ArrayList<Integer>();
                    ArrayList<Integer> listSize = new ArrayList<Integer>();

                    for(Person p2 : activePeople)
                    {
                        if(p.id != p2.id && p.blockchain.verifyChain())
                        {
                            listSize.add(p2.blockchain.blocks.size());
                            p2.ChainSize = p2.blockchain.blocks.size();
                            if(p2.blockchain.blocks.size() > LongestSize)
                            {
                                LongestSize = p2.blockchain.blocks.size();
                            }

                            int x;
                            if(p.blockchain.blocks.size() <= p2.blockchain.blocks.size())
                            {
                               x = p.blockchain.blocks.size() - 1;
                            }
                            else
                            {
                               x = p2.blockchain.blocks.size() - 1;
                            }

                            for(int y = x; y >= 0 ; y--)
                            {
                                if( p.blockchain.blocks.get(y).equals(p2.blockchain.blocks.get(y)))
                                {
                                    //System.out.println("agree");
                                    agreementSize.add(y + 1);
                                    p2.AgrementSize = y +1;

                                    break;
                                }
                            }
                        }
                        else
                        {
                            agreementSize.add(0);
                            p2.AgrementSize = 0;
                            listSize.add(0);
                            p2.ChainSize = 0;

                        }
                    }

                      ArrayList<Person> Candidates = new ArrayList<Person>(activePeople);

                      Collections.sort(Candidates);

                      Votes.add(Candidates.get(Candidates.size() - 1).id);

                }
                
                int Winnerid = getPopularElement(Votes);
                ArrayList<Player> players = new ArrayList<Player>();


                 for(Person p : activePeople)
                 {
                     players.add(new Player(p.id));
                    
                     p.blockchain.appendChain(persons.get(Winnerid).blockchain);
                 }
                 for(Person p : activePeople)
                 {
                      p.blockchain.addBlock(players, i + 1);
                 }
                 
                 if(i == gamesPlayed - 1)
                 {
                     System.out.println(activePeople.get(0).blockchain.blocks.size());
                     averageLastGames += activePeople.get(0).blockchain.blocks.size();
                 }
            }

            double averageGames = 0;
            double mostGames = 0;
            double RepresentetGames = 0;
            ArrayList<Block> blocks = new ArrayList<Block>();
            
            for(Person p : persons)
            {
                averageGames += p.blockchain.blocks.size();

                if(p.blockchain.blocks.size() > mostGames)
                {
                    mostGames = p.blockchain.blocks.size();
                }

                for(Block b : p.blockchain.blocks)
                {
                    if(!blocks.contains(b))
                    {
                        blocks.add(b);
                    }
                }
            }
            averageGames = averageGames/persons.size();
            RepresentetGames = blocks.size();
            
            averageAverageGames += averageGames;
            averageMostGames += mostGames;
            averageRepresentetGames += RepresentetGames;
            
            
            System.out.println(averageGames);
            System.out.println(mostGames);
            System.out.println(RepresentetGames);
            System.out.print(System.currentTimeMillis() - start2);
            System.out.println("---");

        }
        
         averageAverageGames = averageAverageGames/iterations;
         averageMostGames = averageMostGames/iterations;
         averageLastGames = averageLastGames/iterations;
         averageRepresentetGames = averageRepresentetGames/iterations;
         
          System.out.println("===============");
          System.out.println(averageLastGames);
          System.out.println(averageAverageGames);
          System.out.println(averageMostGames);
          System.out.println(averageRepresentetGames);
          System.out.print((System.currentTimeMillis() - start) /iterations);
          
          
    }
    
    public void StandartEncrypted(int population, int gamesPlayed, int minGameSize, int maxGameSize, int iterations)
    {
        long start = System.currentTimeMillis();
        double averageAverageGames = 0;
        double averageMostGames = 0;
        double averageLastGames = 0;
        double averageRepresentetGames = 0;
        
        for(int it = 0; it < iterations ; it++)
        {
            long start2 = System.currentTimeMillis();
            ArrayList<Person> persons = new ArrayList<Person>();
            for(int i = 0; i < population ; i++)
            {
                persons.add(new Person(i));
                persons.get(i).GenerateKeys();
            }

            for(int i = 0; i < gamesPlayed ; i++)
            {
                ArrayList<Person> activePeople = new ArrayList<Person>();
                int nr;
                Random r = new Random();
                if(maxGameSize == minGameSize)
                {
                    nr = maxGameSize;
                }
                else
                {
                    nr = r.nextInt(maxGameSize-minGameSize) + minGameSize;
                }
                while(activePeople.size() < nr)
                {
                     int id = r.nextInt(population);
                     if(!activePeople.contains(persons.get(id)))
                     {
                         activePeople.add(persons.get(id)); 
                     }

                }

                ArrayList<Integer> Votes = new ArrayList<Integer>();

                for(Person p : activePeople)
                {
                    //System.out.println(p.id);
                    int LongestAgreement = 0;
                    int LongestSize = 0;
                    int lowestID = -1;
                    ArrayList<Integer> agreementSize = new ArrayList<Integer>();
                    ArrayList<Integer> listSize = new ArrayList<Integer>();

                        for(Person p2 : activePeople)
                        {
                            if(!p.PublicKeys.containsKey(p2.id))
                            {
                                p.PublicKeys.put(p2.id, p2.publicKey);
                            }
                        
                        
                        if(p.id != p2.id /* && p.blockchain.verifyChain() */)
                        {
                            listSize.add(p2.blockchain.blocks.size());
                            p2.ChainSize = p2.blockchain.blocks.size();
                            if(p2.blockchain.blocks.size() > LongestSize)
                            {
                                LongestSize = p2.blockchain.blocks.size();
                            }

                            int x;
                            if(p.blockchain.blocks.size() <= p2.blockchain.blocks.size())
                            {
                               x = p.blockchain.blocks.size() - 1;
                            }
                            else
                            {
                               x = p2.blockchain.blocks.size() - 1;
                            }

                        }
                        else
                        {
                            agreementSize.add(0);
                            p2.AgrementSize = 0;
                            listSize.add(0);
                            p2.ChainSize = 1;
                        }
                        
                        if(p2.AgrementSize != 0)
                        {   
                            try
                            {
                                p2.VerifiedBlocks = 0;
                                Signature sig = Signature.getInstance("SHA1withRSA");
                                //System.out.println(p2.AgrementSize);
                                for(int y = p2.AgrementSize ; y < p2.ChainSize ; y++)
                                {   
                                    boolean allVerified = true;
                                    int blockId = p2.blockchain.blocks.get(y).id;
                                    byte[] blockBytes = ByteBuffer.allocate(4).putInt(blockId).array();
                                    for(Player pl : p2.blockchain.blocks.get(y).Players)
                                    {
                                        if(p.PublicKeys.containsKey(pl.id))
                                        {
                                            PublicKey key = p.PublicKeys.get(pl.id);

                                            sig.initVerify(key);

                                            sig.update(blockBytes);

                                            if(!sig.verify(pl.signature))
                                            {
                                                allVerified = false;
                                            }
                                        }
                                        else
                                        {
                                            allVerified = false;
                                        }
                                    }
                                     
                                    if(allVerified)
                                    {
                                        p2.VerifiedBlocks ++;
                                    }
                                }
                            }
                            catch (NoSuchAlgorithmException e)
                            {
                                System.err.println("Caught exception " + e.toString());
                            }
                            catch (SignatureException e)
                            {
                                System.err.println("Caught exception " + e.toString());
                            }
                            catch (InvalidKeyException e)
                            {
                                System.err.println("Caught exception " + e.toString());
                            }
                            
                            
                        }
                        
                        p2.PercentVerified = p2.VerifiedBlocks/p2.ChainSize;
                    }

                      ArrayList<Person> Candidates = new ArrayList<Person>(activePeople);

                      Collections.sort(Candidates);

                      Votes.add(Candidates.get(Candidates.size() - 1).id);

                }

                int Winnerid = getPopularElement(Votes);
                ArrayList<Player> players = new ArrayList<Player>();


                 for(Person p : activePeople)
                 {
                     players.add(new Player(p.id, p.GenerateSignature(i + 1)));

                     p.blockchain.appendChain(persons.get(Winnerid).blockchain);
                 }
                 for(Person p : activePeople)
                 {

                      p.blockchain.addBlock(players, i + 1);
                      
                 }
                 
                 UniversalBlocks.blocks.add(persons.get(Winnerid).blockchain.blocks.get(persons.get(Winnerid).blockchain.blocks.size() - 1));
                 
                 if(i == gamesPlayed - 1)
                 {
                     System.out.println(activePeople.get(0).blockchain.blocks.size());
                     averageLastGames += activePeople.get(0).blockchain.blocks.size();
                 }
            }

            double averageGames = 0;
            double mostGames = 0;
            double RepresentetGames = 0;
            ArrayList<Block> blocks = new ArrayList<Block>();
            
            for(Person p : persons)
            {
                averageGames += p.blockchain.blocks.size();

                if(p.blockchain.blocks.size() > mostGames)
                {
                    mostGames = p.blockchain.blocks.size();
                }
                
                for(Block b : p.blockchain.blocks)
                {
                    if(!blocks.contains(b))
                    {
                        blocks.add(b);
                    }
                }
            }
            
            
            averageGames = averageGames/persons.size();
            RepresentetGames = blocks.size();
            
            averageAverageGames += averageGames;
            averageMostGames += mostGames;
            averageRepresentetGames += RepresentetGames;
            
            
            System.out.println(averageGames);
            System.out.println(mostGames);
            System.out.println(RepresentetGames);
             System.out.println(System.currentTimeMillis() - start2);
            System.out.println("---");
        }
        
         averageAverageGames = averageAverageGames/iterations;
         averageMostGames = averageMostGames/iterations;
         averageLastGames = averageLastGames/iterations;
         averageRepresentetGames = averageRepresentetGames/iterations;
         
          System.out.println("===============");
          System.out.println(averageLastGames);
          System.out.println(averageAverageGames);
          System.out.println(averageMostGames);
          System.out.println(averageRepresentetGames);
          System.out.println((System.currentTimeMillis() - start) /iterations);
    }
    
     public void MajorityRulesEncrypted(int population, int gamesPlayed, int minGameSize, int maxGameSize, int iterations)
    {
        long start = System.currentTimeMillis();
        double averageAverageGames = 0;
        double averageMostGames = 0;
        double averageLastGames = 0;
        double averageRepresentetGames = 0;
        
        for(int it = 0; it < iterations ; it++)
        {
            long start2 = System.currentTimeMillis();
            ArrayList<Person> persons = new ArrayList<Person>();
            for(int i = 0; i < population ; i++)
            {
                persons.add(new Person(i));
                persons.get(i).GenerateKeys();
            }

            for(int i = 0; i < gamesPlayed ; i++)
            {
                ArrayList<Person> activePeople = new ArrayList<Person>();
                int nr;
                Random r = new Random();
                if(maxGameSize == minGameSize)
                {
                    nr = maxGameSize;
                }
                else
                {
                    nr = r.nextInt(maxGameSize-minGameSize) + minGameSize;
                }
                while(activePeople.size() < nr)
                {
                     int id = r.nextInt(population);
                     if(!activePeople.contains(persons.get(id)))
                     {
                         activePeople.add(persons.get(id)); 
                     }

                }

                ArrayList<Integer> Votes = new ArrayList<Integer>();

                for(Person p : activePeople)
                {
                    //System.out.println(p.id);
                    int LongestAgreement = 0;
                    int LongestSize = 0;
                    int lowestID = -1;
                    ArrayList<Integer> agreementSize = new ArrayList<Integer>();
                    ArrayList<Integer> listSize = new ArrayList<Integer>();

                        for(Person p2 : activePeople)
                        {
                            if(!p.PublicKeys.containsKey(p2.id))
                            {
                                p.PublicKeys.put(p2.id, p2.publicKey);
                            }
                        
                        
                        if(p.id != p2.id /* && p.blockchain.verifyChain() */)
                        {
                            listSize.add(p2.blockchain.blocks.size());
                            p2.ChainSize = p2.blockchain.blocks.size();
                            if(p2.blockchain.blocks.size() > LongestSize)
                            {
                                LongestSize = p2.blockchain.blocks.size();
                            }

                            int x;
                            if(p.blockchain.blocks.size() <= p2.blockchain.blocks.size())
                            {
                               x = p.blockchain.blocks.size() - 1;
                            }
                            else
                            {
                               x = p2.blockchain.blocks.size() - 1;
                            }

                            for(int y = x; y >= 0 ; y--)
                            {
                                if( p.blockchain.blocks.get(y).equals(p2.blockchain.blocks.get(y)))
                                {

                                    agreementSize.add(y + 1);
                                    p2.AgrementSize = y +1;

                                    
                                    if(y > LongestAgreement)
                                    {
                                        LongestAgreement = y + 1;                                   
                                    }
                                    break;
                                }
                            }
                        }

                        else
                        {
                            agreementSize.add(0);
                            p2.AgrementSize = 0;
                            listSize.add(0);
                            p2.ChainSize = 1;
                        }
                        
                        if(p2.AgrementSize != 0)
                        {   
                            try
                            {
                                p2.VerifiedBlocks = 0;
                                Signature sig = Signature.getInstance("SHA1withRSA");
                                //System.out.println(p2.AgrementSize);
                                for(int y = p2.AgrementSize ; y < p2.ChainSize ; y++)
                                {   
                                    boolean allVerified = true;
                                    int blockId = p2.blockchain.blocks.get(y).id;
                                    byte[] blockBytes = ByteBuffer.allocate(4).putInt(blockId).array();
                                    for(Player pl : p2.blockchain.blocks.get(y).Players)
                                    {
                                        if(p.PublicKeys.containsKey(pl.id))
                                        {
                                            PublicKey key = p.PublicKeys.get(pl.id);

                                            sig.initVerify(key);

                                            sig.update(blockBytes);

                                            if(!sig.verify(pl.signature))
                                            {
                                                allVerified = false;
                                            }
                                        }
                                        else
                                        {
                                            allVerified = false;
                                        }
                                    }
                                     
                                    if(allVerified)
                                    {
                                        p2.VerifiedBlocks ++;
                                    }
                                }
                            }
                            catch (NoSuchAlgorithmException e)
                            {
                                System.err.println("Caught exception " + e.toString());
                            }
                            catch (SignatureException e)
                            {
                                System.err.println("Caught exception " + e.toString());
                            }
                            catch (InvalidKeyException e)
                            {
                                System.err.println("Caught exception " + e.toString());
                            }
                            
                            
                        }
                        
                        p2.PercentVerified = p2.VerifiedBlocks/p2.ChainSize;
                    }

                      ArrayList<Person> Candidates = new ArrayList<Person>(activePeople);

                      Collections.sort(Candidates);

                      for(Person c : Candidates)
                      {
                          System.out.println(c.AgrementSize + " " + c.ChainSize + " " + c.id + "next");
                      }

                      Votes.add(Candidates.get(Candidates.size() - 1).id);

                }

                int Winnerid = getPopularElement(Votes);
                ArrayList<Player> players = new ArrayList<Player>();


                 for(Person p : activePeople)
                 {
                     players.add(new Player(p.id, p.GenerateSignature(i + 1)));
 
                     p.blockchain.appendChain(persons.get(Winnerid).blockchain);
                 }
                 for(Person p : activePeople)
                 {
                     //System.out.println("copy");
                      p.blockchain.addBlock(players, i + 1);
                      
                 }
                 
                 UniversalBlocks.blocks.add(persons.get(Winnerid).blockchain.blocks.get(persons.get(Winnerid).blockchain.blocks.size() - 1));
                 
                 if(i == gamesPlayed - 1)
                 {
                     System.out.println(activePeople.get(0).blockchain.blocks.size());
                     averageLastGames += activePeople.get(0).blockchain.blocks.size();
                 }
                 //System.out.println("---");
            }

            double averageGames = 0;
            double mostGames = 0;
            double RepresentetGames = 0;
            ArrayList<Block> blocks = new ArrayList<Block>();
            
            for(Person p : persons)
            {
                averageGames += p.blockchain.blocks.size();

                if(p.blockchain.blocks.size() > mostGames)
                {
                    mostGames = p.blockchain.blocks.size();
                }
                
                for(Block b : p.blockchain.blocks)
                {
                    if(!blocks.contains(b))
                    {
                        blocks.add(b);
                    }
                }
            }
            
            
            averageGames = averageGames/persons.size();
            RepresentetGames = blocks.size();

            averageAverageGames += averageGames;
            averageMostGames += mostGames;
            averageRepresentetGames += RepresentetGames;
            
            
            System.out.println(averageGames);
            System.out.println(mostGames);
            System.out.println(RepresentetGames);
             System.out.println(System.currentTimeMillis() - start2);
            System.out.println("---");
        }
        
         averageAverageGames = averageAverageGames/iterations;
         averageMostGames = averageMostGames/iterations;
         averageLastGames = averageLastGames/iterations;
         averageRepresentetGames = averageRepresentetGames/iterations;
         
          System.out.println("===============");
          System.out.println(averageLastGames);
          System.out.println(averageAverageGames);
          System.out.println(averageMostGames);
          System.out.println(averageRepresentetGames);
          System.out.println((System.currentTimeMillis() - start) /iterations);
    }
    
    public void MajorityRulesEnctpted2(int population, int gamesPlayed, int minGameSize, int maxGameSize, int iterations)
    {
       long start = System.currentTimeMillis();
        double averageAverageGames = 0;
        double averageMostGames = 0;
        double averageLastGames = 0;
        double averageRepresentetGames = 0;
        
        Signature sig;
        
        try
        {
            sig = Signature.getInstance("SHA1withRSA");
        }
        catch (Exception e)
        {
            System.err.println("Caught exception " + e.toString());
            sig = null;
        }
        
        for(int it = 0; it < iterations ; it++)
        {
            long start2 = System.currentTimeMillis();
            ArrayList<Person> persons = new ArrayList<Person>();
            for(int i = 0; i < population ; i++)
            {
                persons.add(new Person(i));
                persons.get(i).GenerateKeys();
            }

            for(int i = 0; i < gamesPlayed ; i++)
            {
                ArrayList<Person> activePeople = new ArrayList<Person>();
                int nr;
                Random r = new Random();
                if(maxGameSize == minGameSize)
                {
                    nr = maxGameSize;
                }
                else
                {
                    nr = r.nextInt(maxGameSize-minGameSize) + minGameSize;
                }
                while(activePeople.size() < nr)
                {
                     int id = r.nextInt(population);
                     if(!activePeople.contains(persons.get(id)))
                     {
                         activePeople.add(persons.get(id)); 
                     }

                }

                ArrayList<Integer> Votes = new ArrayList<Integer>();

                for(Person p : activePeople)
                {
                    //System.out.println(p.id);
                    int LongestAgreement = 0;
                    int LongestSize = 0;
                    int lowestID = -1;
                    p.ids.clear();
                    ArrayList<Integer> agreementSize = new ArrayList<Integer>();
                    ArrayList<Integer> listSize = new ArrayList<Integer>();
                    ArrayList<Integer> ids = new ArrayList<Integer>();

                        for(Person p2 : activePeople)
                        {
                            if(!p.PublicKeys.containsKey(p2.id))
                            {
                                p.PublicKeys.put(p2.id, p2.publicKey);
                            }
                        
                        
                        if(p.id != p2.id && p.blockchain.verifyChain())
                        {
                            listSize.add(p2.blockchain.blocks.size());
                            p2.ChainSize = p2.blockchain.blocks.size();
                            if(p2.blockchain.blocks.size() > LongestSize)
                            {
                                LongestSize = p2.blockchain.blocks.size();
                            }

                            int x;
                            if(p.blockchain.blocks.size() <= p2.blockchain.blocks.size())
                            {
                               x = p.blockchain.blocks.size() - 1;
                            }
                            else
                            {
                               x = p2.blockchain.blocks.size() - 1;
                            }

                            for(int y = x; y >= 0 ; y--)
                            {
                                if( p.blockchain.blocks.get(y).equals(p2.blockchain.blocks.get(y)))
                                {
                                    //System.out.println("agree");
                                    agreementSize.add(y + 1);
                                    p2.AgrementSize = y +1;

                                    break;
                                }
                                else
                                {
                                    p.ids.add(p.blockchain.blocks.get(y).id);
                                }
                            }
                        }

                        else
                        {
                            agreementSize.add(0);
                            p2.AgrementSize = 0;
                            listSize.add(0);
                            p2.ChainSize = 1;

                        }
                        
                        if(p2.AgrementSize !=0)
                        {   
                            try
                            {
                                p2.VerifiedBlocks = 0;
                                p.Vblocks.clear();
                                //Signature sig = Signature.getInstance("SHA1withRSA");
                                for(int y = p2.AgrementSize ; y < p2.ChainSize ; y++)
                                {   
                                    
                                    boolean allVerified = true;
                                    int blockId = p2.blockchain.blocks.get(y).id;
                                    byte[] blockBytes = ByteBuffer.allocate(4).putInt(blockId).array();
                                    for(Player pl : p2.blockchain.blocks.get(y).Players)
                                    {
                                        if(p.PublicKeys.containsKey(pl.id))
                                        {
                                            PublicKey key = p.PublicKeys.get(pl.id);

                                            sig.initVerify(key);

                                            sig.update(blockBytes);

                                            if(!sig.verify(pl.signature))
                                            {
                                                allVerified = false;
                                            }
                                        }
                                        else
                                        {
                                            allVerified = false;
                                        }
                                    }
                                     
                                    if(allVerified)
                                    {
                                        p2.VerifiedBlocks ++;
                                        
                                        if(p.ids.contains(p2.blockchain.blocks.get(y).id))
                                        {
                                            p.Vblocks.add(p2.blockchain.blocks.get(y));
                                        }
                                        
                                        
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                                System.err.println("Caught exception " + e.toString());
                            }
                            
                            
                        }
                        
                        p2.PercentVerified = p2.VerifiedBlocks/p2.ChainSize;
                    }

                      ArrayList<Person> Candidates = new ArrayList<Person>(activePeople);

                      Collections.sort(Candidates);

    //                  for(Person c : Candidates)
    //                  {
    //                      System.out.println(c.AgrementSize + " " + c.ChainSize + " " + c.id + "next");
    //                  }

                      Votes.add(Candidates.get(Candidates.size() - 1).id);

                }

//                for(int v : Votes)
//                {
//                    System.out.println(v);
//                }

                int Winnerid = getPopularElement(Votes);
                ArrayList<Player> players = new ArrayList<Player>();


                 for(Person p : activePeople)
                 {
                     players.add(new Player(p.id /*, p.GenerateSignature(i + 1)*/));
                     //System.out.println("add");
                     //p.blockchain.blocks = new ArrayList<Block>(persons.get(Winnerid).blockchain.blocks);
                     //System.out.println("Size: " + p.blockchain.blocks.size() + " ID: " + p.id);
                     p.blockchain.appendChain(persons.get(Winnerid).blockchain);
                     p.blockchain.addBlocks(persons.get(Winnerid).Vblocks);
                 }
                 for(Person p : activePeople)
                 {
                     //System.out.println("copy");
                      p.blockchain.addBlock(players, i + 1);
                 }
                 
                 if(i == gamesPlayed - 1)
                 {
                     System.out.println(activePeople.get(0).blockchain.blocks.size());
                     averageLastGames += activePeople.get(0).blockchain.blocks.size();
                 }
                 //System.out.println("---");
            }

            double averageGames = 0;
            double mostGames = 0;
            double RepresentetGames = 0;
            ArrayList<Block> blocks = new ArrayList<Block>();
            ArrayList<Integer> ids = new ArrayList<Integer>();
            
            for(Person p : persons)
            {
                averageGames += p.blockchain.blocks.size();

                if(p.blockchain.blocks.size() > mostGames)
                {
                    mostGames = p.blockchain.blocks.size();
                }
                
                for(Block b : p.blockchain.blocks)
                {
                    if(!ids.contains(b.id))
                    {
                        ids.add(b.id);
                        blocks.add(b);
                    }
                }
                
            }
            averageGames = averageGames/persons.size();
            RepresentetGames = blocks.size();
            
            averageAverageGames += averageGames;
            averageMostGames += mostGames;
            averageRepresentetGames += RepresentetGames;
            
          
            System.out.println(averageGames);
            System.out.println(mostGames);
            System.out.println(RepresentetGames);
            System.out.println(System.currentTimeMillis() - start2);
            System.out.println("---");
            

    //        for(Block b : persons.get(0).blockchain.blocks)
    //        {
    //           System.out.println(b.getHash());
    //        }
        }
        
         averageAverageGames = averageAverageGames/iterations;
         averageMostGames = averageMostGames/iterations;
         averageLastGames = averageLastGames/iterations;
         averageRepresentetGames = averageRepresentetGames/iterations;
         
          System.out.println("===============");
          System.out.println(averageLastGames);
          System.out.println(averageAverageGames);
          System.out.println(averageMostGames);
         
          System.out.println(averageRepresentetGames);
          System.out.println((System.currentTimeMillis() - start) /iterations);
    }
    
    public void TakeAll(int population, int gamesPlayed, int minGameSize, int maxGameSize, int iterations)
    {
        long start = System.currentTimeMillis();
        double averageAverageGames = 0;
        double averageMostGames = 0;
        double averageLastGames = 0;
        double averageRepresentetGames = 0;
        
        for(int it = 0; it < iterations ; it++)
        {
            long start2 = System.currentTimeMillis();
            ArrayList<Person> persons = new ArrayList<Person>();
            for(int i = 0; i < population ; i++)
            {
                persons.add(new Person(i));
            }

            for(int i = 0; i < gamesPlayed ; i++)
            {
                ArrayList<Person> activePeople = new ArrayList<Person>();
                int nr;
                Random r = new Random();
                if(maxGameSize == minGameSize)
                {
                    nr = maxGameSize;
                }
                else
                {
                    nr = r.nextInt(maxGameSize-minGameSize) + minGameSize;
                }
                while(activePeople.size() < nr)
                {
                     int id = r.nextInt(population);
                     if(!activePeople.contains(persons.get(id)))
                     {
                         activePeople.add(persons.get(id)); 
                     }

                }
                
                ArrayList<Blockchain> bcs = new ArrayList<Blockchain>();
                for(Person p : activePeople)
                {
                    bcs.add(p.blockchain);
                }
                
                ArrayList<Block> blocks = new ArrayList<Block>();
                
                ArrayList<Player> players = new ArrayList<Player>();
                
                blocks.addAll(activePeople.get(0).blockchain.appendChain(activePeople));
                for(Person p : activePeople)
                {
                    players.add(new Player(p.id));
                }
                for(Person p : activePeople)
                {
                    p.blockchain.addBlocks(blocks);
                    p.blockchain.addBlock(players, i + 1);
                    //System.out.println(p.blockchain.blocks.size());
                }
                
                if(i == gamesPlayed - 1)
                 {
                     System.out.println(activePeople.get(0).blockchain.blocks.size());
                     averageLastGames += activePeople.get(0).blockchain.blocks.size();
                 }
            }
            
            double averageGames = 0;
            double mostGames = 0;
            double RepresentetGames = 0;
            ArrayList<Integer> blocks = new ArrayList<Integer>();
            
            for(Person p : persons)
            {
                averageGames += p.blockchain.blocks.size();

                if(p.blockchain.blocks.size() > mostGames)
                {
                    mostGames = p.blockchain.blocks.size();
                }

                for(Block b : p.blockchain.blocks)
                {
                    if(!blocks.contains(b.id))
                    {
                        blocks.add(b.id);
                    }
                }
            }
            averageGames = averageGames/persons.size();
            RepresentetGames = blocks.size();
            
            averageAverageGames += averageGames;
            averageMostGames += mostGames;
            averageRepresentetGames += RepresentetGames;
            
            
            System.out.println(averageGames);
            System.out.println(mostGames);
            System.out.println(RepresentetGames);
            System.out.println(System.currentTimeMillis() - start2);
            System.out.println("---");
        }
        
          averageAverageGames = averageAverageGames/iterations;
         averageMostGames = averageMostGames/iterations;
         averageLastGames = averageLastGames/iterations;
         averageRepresentetGames = averageRepresentetGames/iterations;
         
          System.out.println("===============");
          System.out.println(averageLastGames);
          System.out.println(averageAverageGames);
          System.out.println(averageMostGames);
          System.out.println(averageRepresentetGames);
          System.out.println((System.currentTimeMillis() - start) /iterations);
    }
    
    public void TakeAllEncrypted(int population, int gamesPlayed, int minGameSize, int maxGameSize, int iterations)
    {
        long start = System.currentTimeMillis();
        double averageAverageGames = 0;
        double averageMostGames = 0;
        double averageLastGames = 0;
        double averageRepresentetGames = 0;
        
          Signature sig;
        
        try
        {
            sig = Signature.getInstance("SHA1withRSA");
        }
        catch (Exception e)
        {
            System.err.println("Caught exception " + e.toString());
            sig = null;
        }
        
        for(int it = 0; it < iterations ; it++)
        {
            long start2 = System.currentTimeMillis();
            ArrayList<Person> persons = new ArrayList<Person>();
            for(int i = 0; i < population ; i++)
            {
                persons.add(new Person(i));
                persons.get(i).GenerateKeys();
            }

            for(int i = 0; i < gamesPlayed ; i++)
            {
                ArrayList<Person> activePeople = new ArrayList<Person>();
                ArrayList<Block> otherBlocks = new ArrayList<Block>();
                
                int nr;
                Random r = new Random();
                if(maxGameSize == minGameSize)
                {
                    nr = maxGameSize;
                }
                else
                {
                    nr = r.nextInt(maxGameSize-minGameSize) + minGameSize;
                }
                while(activePeople.size() < nr)
                {
                     int id = r.nextInt(population);
                     if(!activePeople.contains(persons.get(id)))
                     {
                         activePeople.add(persons.get(id)); 
                     }

                }
                
                
                ArrayList<Blockchain> bcs = new ArrayList<Blockchain>();
                for(Person p : activePeople)
                {
                     for(Person p2 : activePeople)
                    {
                        if(!p.PublicKeys.containsKey(p2.id))
                        {
                            p.PublicKeys.put(p2.id, p2.publicKey);
                        }
                    }
                }
                
                ArrayList<Player> players = new ArrayList<Player>();
                
                otherBlocks.addAll(activePeople.get(0).blockchain.appendChain(activePeople));
                for(Person p : activePeople)
                {
                    players.add(new Player(p.id, p.GenerateSignature(i + 1)));
                }
                
                ArrayList<Block> verifiedBlocks = new ArrayList<Block>();
               
                for(Block b : otherBlocks)
                { 
                    boolean allVerified = true;
                    int blockId = b.id;
                    byte[] blockBytes = ByteBuffer.allocate(4).putInt(blockId).array();

                     for(Person p : activePeople)
                     {
                        for(Player pl : b.Players)
                        {
                            if(p.PublicKeys.containsKey(pl.id))
                            {
                                try
                                {
                                    PublicKey key = p.PublicKeys.get(pl.id);

                                    sig.initVerify(key);

                                    sig.update(blockBytes);

                                    if(!sig.verify(pl.signature))
                                    {
                                        allVerified = false;
                                        System.out.println("OtherBlock Discard " + b.id);
                                        break;
                                    }
                                }
                                catch (Exception e)
                                {
                                    allVerified = false;
                                    System.err.println("Caught exception " + e.toString());
                                    break;
                                }
                            }
                            else
                            {
                                allVerified = false;
                                System.out.println("OtherBlock Discard " + b.id);
                                break;
                            }
                        }
                     }
                     
                     if (allVerified)
                     {
                         verifiedBlocks.add(b);
                     }
                }
                
                for(Person p : activePeople)
                { 
                    p.blockchain.addBlocks(verifiedBlocks);
                    p.blockchain.addBlock(players, i + 1);
                    System.out.println(p.blockchain.blocks.size());
                }
                
                if(i == gamesPlayed - 1)
                 {
                     System.out.println(activePeople.get(0).blockchain.blocks.size());
                     averageLastGames += activePeople.get(0).blockchain.blocks.size();
                 }
            }
            
            double averageGames = 0;
            double mostGames = 0;
            double RepresentetGames = 0;
            ArrayList<Integer> blocks = new ArrayList<Integer>();
            
            for(Person p : persons)
            {
                averageGames += p.blockchain.blocks.size();

                if(p.blockchain.blocks.size() > mostGames)
                {
                    mostGames = p.blockchain.blocks.size();
                }

                for(Block b : p.blockchain.blocks)
                {
                    if(!blocks.contains(b.id))
                    {
                        blocks.add(b.id);
                    }
                }
            }
            averageGames = averageGames/persons.size();
            RepresentetGames = blocks.size();
            
            averageAverageGames += averageGames;
            averageMostGames += mostGames;
            averageRepresentetGames += RepresentetGames;
            
            
            System.out.println(averageGames);
            System.out.println(mostGames);
            System.out.println(RepresentetGames);
            System.out.println(System.currentTimeMillis() - start2);
            System.out.println("---");
            
            for(Block b : persons.get(0).blockchain.blocks)
            {
               System.out.println(b.nounce);
            }
             System.out.println(persons.get(0).blockchain.verifyChain());
        }
        
          averageAverageGames = averageAverageGames/iterations;
         averageMostGames = averageMostGames/iterations;
         averageLastGames = averageLastGames/iterations;
         averageRepresentetGames = averageRepresentetGames/iterations;
         
          System.out.println("===============");
          System.out.println(averageLastGames);
          System.out.println(averageAverageGames);
          System.out.println(averageMostGames);
          System.out.println(averageRepresentetGames);
          System.out.println((System.currentTimeMillis() - start) /iterations);
         
    }
    
    public void JustHashMapsEncrypted(int population, int gamesPlayed, int minGameSize, int maxGameSize, int iterations)
    {
        long start = System.currentTimeMillis();
        double averageAverageGames = 0;
        double averageMostGames = 0;
        double averageLastGames = 0;
        double averageRepresentetGames = 0;
        
        Signature sig;
        
        try
        {
            sig = Signature.getInstance("SHA1withRSA");
        }
        catch (Exception e)
        {
            System.err.println("Caught exception " + e.toString());
            sig = null;
        }
        
        for(int it = 0; it < iterations ; it++)
        {
            long start2 = System.currentTimeMillis();
            ArrayList<Person> persons = new ArrayList<Person>();
            for(int i = 0; i < population ; i++)
            {
                persons.add(new Person(i));
                //persons.get(i).GenerateKeys();
            }

            for(int i = 0; i < gamesPlayed ; i++)
            {
                ArrayList<Person> activePeople = new ArrayList<Person>();
                ArrayList<Block> otherBlocks = new ArrayList<Block>();
                
                int nr;
                Random r = new Random();
                if(maxGameSize == minGameSize)
                {
                    nr = maxGameSize;
                }
                else
                {
                    nr = r.nextInt(maxGameSize-minGameSize) + minGameSize;
                }
                while(activePeople.size() < nr)
                {
                     int id = r.nextInt(population);
                     if(!activePeople.contains(persons.get(id)))
                     {
                         activePeople.add(persons.get(id)); 
                     }

                }
                
                for(Person p : activePeople)
                {
                    for(Person p2 : activePeople)
                    {
                        if(!p.PublicKeys.containsKey(p2.id))
                        {
                            p.PublicKeys.put(p2.id, p2.publicKey);
                        }
                    }
                }
                ArrayList<Player> players = new ArrayList<Player>();
                for(Person p : activePeople)
                {
                    players.add(new Player(p.id, p.GenerateSignature(i + 1)));
                }
                
                
                
                for(Person p : activePeople)
                {
                    for(Person p2 : activePeople)
                    {
                        Iterator iterator = p2.PendingBlocks.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry pair = (Map.Entry)iterator.next();
                            
                            Block b = (Block)pair.getValue();
                            if(b.getHash().equals((String)pair.getKey()))
                            {
                                p.PendingBlocks.putIfAbsent((String)pair.getKey(), b);
                            }
                             
                        }
                    }
                    
                    int ValidCount = 0;
                    boolean allVerified = true;
                    
                     Iterator iterator = p.PendingBlocks.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry pair = (Map.Entry)iterator.next();
                            Block b = (Block)pair.getValue();
                            int blockId = b.id;
                            byte[] blockBytes = ByteBuffer.allocate(4).putInt(blockId).array();
                            for(Player pl : b.Players)
                            {
                                if(p.PublicKeys.containsKey(pl.id) && !p.AcceptedBlocks.containsKey((String)pair.getKey()))
                                {
                                    try
                                    {
                                        PublicKey key = p.PublicKeys.get(pl.id);
    
                                        sig.initVerify(key);
    
                                        sig.update(blockBytes);
    
                                        if(!sig.verify(pl.signature))
                                        {
                                            allVerified = false;
                                            System.out.println("OtherBlock Discard " + b.id);
                                            break;
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        allVerified = false;
                                        System.err.println("Caught exception " + e.toString());
                                        break;
                                    }
                                    ValidCount ++;
                                }
                                else
                                {
                                    ValidCount --;
                                    allVerified = false;
                                }
                            }
                            
                            if (ValidCount > 0)
                            {
                                 p.AcceptedBlocks.put((String)pair.getKey(), (Block)pair.getValue());
                                 //System.out.println("added block to Accepted " + p.AcceptedBlocks.size());
                            }
                        }
                }
                
                for(Person p : activePeople)
                { 
                   Block b = new Block(players, i + 1);
                   p.AcceptedBlocks.put(b.getHash(), b);
                   p.PendingBlocks.put(b.getHash(), b);
                   //System.out.println(p.AcceptedBlocks.size());
                }
                
                if(i == gamesPlayed - 1)
                 {
                     System.out.println(activePeople.get(0).AcceptedBlocks.size());
                     averageLastGames += activePeople.get(0).AcceptedBlocks.size();
                 }
            }
            
            double averageGames = 0;
            double mostGames = 0;
            double RepresentetGames = 0;
            Map<String, Block> Blocks = new HashMap<String, Block>();
            
            for(Person p : persons)
            {
                averageGames += p.AcceptedBlocks.size();

                if(p.AcceptedBlocks.size() > mostGames)
                {
                    mostGames = p.AcceptedBlocks.size();
                }

                Blocks.putAll(p.AcceptedBlocks);
            }
            averageGames = averageGames/persons.size();
            RepresentetGames = Blocks.size();
            
            averageAverageGames += averageGames;
            averageMostGames += mostGames;
            averageRepresentetGames += RepresentetGames;
            
            
            System.out.println(averageGames);
            System.out.println(mostGames);
            System.out.println(RepresentetGames);
            System.out.println(System.currentTimeMillis() - start2);
            System.out.println("---");
            
//            for(Block b : persons.get(0).blockchain.blocks)
//            {
//               System.out.println(b.nounce);
//            }
//             System.out.println(persons.get(0).blockchain.verifyChain());
        }
        
          averageAverageGames = averageAverageGames/iterations;
         averageMostGames = averageMostGames/iterations;
         averageLastGames = averageLastGames/iterations;
         averageRepresentetGames = averageRepresentetGames/iterations;
         
          System.out.println("===============");
          System.out.println(averageLastGames);
          System.out.println(averageAverageGames);
          System.out.println(averageMostGames);
          System.out.println(averageRepresentetGames);
          System.out.println((System.currentTimeMillis() - start) /iterations);
         
    }
    
     public void JustHashMapsMoreEncrypted(int population, int gamesPlayed, int minGameSize, int maxGameSize, int iterations)
    {
        long start = System.currentTimeMillis();
        double averageAverageGames = 0;
        double averageMostGames = 0;
        double averageLastGames = 0;
        double averageRepresentetGames = 0;
        
        Signature sig;
        
        try
        {
            sig = Signature.getInstance("SHA1withRSA");
        }
        catch (Exception e)
        {
            System.err.println("Caught exception " + e.toString());
            sig = null;
        }
        
        for(int it = 0; it < iterations ; it++)
        {
            long start2 = System.currentTimeMillis();
            ArrayList<Person> persons = new ArrayList<Person>();
            for(int i = 0; i < population ; i++)
            {
                persons.add(new Person(i));
                //persons.get(i).GenerateKeys();
            }
            

            for(int i = 0; i < gamesPlayed ; i++)
            {
                ArrayList<Person> activePeople = new ArrayList<Person>();
                ArrayList<Block> otherBlocks = new ArrayList<Block>();
                
                int nr;
                Random r = new Random();
                if(maxGameSize == minGameSize)
                {
                    nr = maxGameSize;
                }
                else
                {
                    nr = r.nextInt(maxGameSize-minGameSize) + minGameSize;
                }
                while(activePeople.size() < nr)
                {
                     int id = r.nextInt(population);
                     if(!activePeople.contains(persons.get(id)))
                     {
                         activePeople.add(persons.get(id)); 
                     }

                }
                
                for(Person p : activePeople)
                {
                    for(Person p2 : activePeople)
                    {
                        if(!p.PublicKeys.containsKey(p2.id))
                        {
                            p.PublicKeys.put(p2.id, p2.publicKey);
                        }
                    }
                }
                ArrayList<Player> players = new ArrayList<Player>();
                for(Person p : activePeople)
                {
                    players.add(new Player(p.id/*, p.GenerateSignature(i + 1)*/));
                }
                
                
                
                for(Person p : activePeople)
                {
                    for(Person p2 : activePeople)
                    {
                        Iterator iterator = p2.AcceptedBlocks.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry pair = (Map.Entry)iterator.next();
                            
                            Block b = (Block)pair.getValue();
//                            if(b.getHash().equals((String)pair.getKey()))
//                            {
                                p.PendingBlocks.putIfAbsent((String)pair.getKey(), b);
                                //System.out.println("added block to Pending " + p.PendingBlocks.size());
//                            }
                            //iterator.remove(); // avoids a ConcurrentModificationException
                        }
                    }
                    
                    boolean allVerified = true;
                    int ValidationCount = 0;
                    
                     Iterator iterator = p.PendingBlocks.entrySet().iterator();
                        while (iterator.hasNext()) {
                       
                            Map.Entry pair = (Map.Entry)iterator.next();
                            Block b = (Block)pair.getValue();
                            int blockId = b.id;
                            byte[] blockBytes = ByteBuffer.allocate(4).putInt(blockId).array();
                            int loserid = b.Players.size()/2 - 1;
                            List<Player> losers = b.Players.subList(loserid, b.Players.size());
                            for(Player pl : losers)
                            {
                                if(p.PublicKeys.containsKey(pl.id) && !p.AcceptedBlocks.containsKey((String)pair.getKey()))
                                {
                                    try
                                    {
                                        PublicKey key = p.PublicKeys.get(pl.id);
    
                                        sig.initVerify(key);
    
                                        sig.update(blockBytes);
    
                                        if(!sig.verify(pl.signature))
                                        {
                                            allVerified = false;
                                            System.out.println("OtherBlock Discard " + b.id);
                                            break;
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        allVerified = false;
                                        System.err.println("Caught exception " + e.toString());
                                        break;
                                    }
                                    ValidationCount ++;
                                }
                                else
                                {
                                    allVerified = false;
                                    ValidationCount --;

                                    break;
                                }
                            }

                            
                            if (ValidationCount > 0)
                            {
                                 p.AcceptedBlocks.put((String)pair.getKey(), (Block)pair.getValue());
                                 System.out.println("added block to Accepted " + b.id);
                            }

                        }
                }
                
                for(Person p : activePeople)
                { 
                   Block b = new Block(players, i + 1);
                   p.AcceptedBlocks.put(b.getHash(), b);
                   p.PendingBlocks.clear();
                   //System.out.println(p.AcceptedBlocks.size());
                }
                
                if(i == gamesPlayed - 1)
                 {
                     System.out.println(activePeople.get(0).AcceptedBlocks.size());
                     averageLastGames += activePeople.get(0).AcceptedBlocks.size();
                 }
            }
            
            double averageGames = 0;
            double mostGames = 0;
            double RepresentetGames = 0;
            Map<String, Block> Blocks = new HashMap<String, Block>();
            
            for(Person p : persons)
            {
                averageGames += p.AcceptedBlocks.size();

                if(p.AcceptedBlocks.size() > mostGames)
                {
                    mostGames = p.AcceptedBlocks.size();
                }

                Blocks.putAll(p.AcceptedBlocks);
            }
            averageGames = averageGames/persons.size();
            RepresentetGames = Blocks.size();
            
            averageAverageGames += averageGames;
            averageMostGames += mostGames;
            averageRepresentetGames += RepresentetGames;
            
            
            System.out.println(averageGames);
            System.out.println(mostGames);
            System.out.println(RepresentetGames);
            System.out.println(System.currentTimeMillis() - start2);
            System.out.println("---");

        }
        
          averageAverageGames = averageAverageGames/iterations;
         averageMostGames = averageMostGames/iterations;
         averageLastGames = averageLastGames/iterations;
         averageRepresentetGames = averageRepresentetGames/iterations;
         
          System.out.println("===============");
          System.out.println(averageLastGames);
          System.out.println(averageAverageGames);
          System.out.println(averageMostGames);
          System.out.println(averageRepresentetGames);
          System.out.println((System.currentTimeMillis() - start) /iterations);
         
    }
    
    public void JustHashMaps(int population, int gamesPlayed, int minGameSize, int maxGameSize, int iterations)
    {
        long start = System.currentTimeMillis();
        double averageAverageGames = 0;
        double averageMostGames = 0;
        double averageLastGames = 0;
        double averageRepresentetGames = 0;
        
        
        for(int it = 0; it < iterations ; it++)
        {
            long start2 = System.currentTimeMillis();
            ArrayList<Person> persons = new ArrayList<Person>();
            for(int i = 0; i < population ; i++)
            {
                persons.add(new Person(i));
            }

            for(int i = 0; i < gamesPlayed ; i++)
            {
                ArrayList<Person> activePeople = new ArrayList<Person>();
                ArrayList<Block> otherBlocks = new ArrayList<Block>();
                
                int nr;
                Random r = new Random();
                if(maxGameSize == minGameSize)
                {
                    nr = maxGameSize;
                }
                else
                {
                    nr = r.nextInt(maxGameSize-minGameSize) + minGameSize;
                }
                while(activePeople.size() < nr)
                {
                     int id = r.nextInt(population);
                     if(!activePeople.contains(persons.get(id)))
                     {
                         activePeople.add(persons.get(id)); 
                     }

                }
                
                ArrayList<Player> players = new ArrayList<Player>();
                for(Person p : activePeople)
                {
                    players.add(new Player(p.id));
                }
                
                
                
                for(Person p : activePeople)
                {
                    for(Person p2 : activePeople)
                    {
                        p.AcceptedBlocks.putAll(p2.AcceptedBlocks);
                    }
                }
                
                for(Person p : activePeople)
                { 
                   Block b = new Block(players, i + 1);
                   p.AcceptedBlocks.put(b.getHash(), b);
                }
                
                if(i == gamesPlayed - 1)
                 {
                     System.out.println(activePeople.get(0).AcceptedBlocks.size());
                     averageLastGames += activePeople.get(0).AcceptedBlocks.size();
                 }
            }
            
            double averageGames = 0;
            double mostGames = 0;
            double RepresentetGames = 0;
            Map<String, Block> Blocks = new HashMap<String, Block>();
            
            for(Person p : persons)
            {
                averageGames += p.AcceptedBlocks.size();

                if(p.AcceptedBlocks.size() > mostGames)
                {
                    mostGames = p.AcceptedBlocks.size();
                }

                Blocks.putAll(p.AcceptedBlocks);
            }
            averageGames = averageGames/persons.size();
            RepresentetGames = Blocks.size();
            
            averageAverageGames += averageGames;
            averageMostGames += mostGames;
            averageRepresentetGames += RepresentetGames;
            
            
            System.out.println(averageGames);
            System.out.println(mostGames);
            System.out.println(RepresentetGames);
            System.out.println(System.currentTimeMillis() - start2);
            System.out.println("---");
        }
        
          averageAverageGames = averageAverageGames/iterations;
         averageMostGames = averageMostGames/iterations;
         averageLastGames = averageLastGames/iterations;
         averageRepresentetGames = averageRepresentetGames/iterations;
         
          System.out.println("===============");
          System.out.println(averageLastGames);
          System.out.println(averageAverageGames);
          System.out.println(averageMostGames);
          System.out.println(averageRepresentetGames);
          System.out.println((System.currentTimeMillis() - start) /iterations);
         
    }
    
    public int getPopularElement(ArrayList<Integer> a)
    {
      int count = 1, tempCount;
      int popular = a.get(0);
      int temp = 0;
      for (int i = 0; i < (a.size() - 1); i++)
      {
        temp = a.get(i);
        tempCount = 0;
        for (int j = 1; j < a.size(); j++)
        {
          if (temp == a.get(j))
            tempCount++;
        }
        if (tempCount > count)
        {
          popular = temp;
          count = tempCount;
        }
      }
      return popular;
    }
}
