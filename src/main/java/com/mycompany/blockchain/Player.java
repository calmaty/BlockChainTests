/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.blockchain;

import java.io.Serializable;

/**
 *
 * @author Christoffer
 */
public class Player implements Serializable {
    String name;
    int id;
    byte [] signature;

    public Player(int id) {
        this.id = id;
    }

    public Player(int id, byte[] signature) {
        this.id = id;
        this.signature = signature;
    }
    
    
}
