/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import model.Cart;
/**
 *
 * @author it353f608
 */
public interface CartDAO {
    public int createCart();
    public ArrayList<Cart> getCartByEmail(String email);
}
