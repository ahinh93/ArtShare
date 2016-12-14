/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import java.util.ArrayList;
import model.WeeklyWinners;

/**
 *
 * @author Jesse
 */
public interface WeeklyWinnersDAO {
    public ArrayList<WeeklyWinners> findAllWinners();
    public void payout(WeeklyWinners winner);
    public void getAuthorsForWinners(WeeklyWinners winner, int id);
    public void payoutForWinner(Date date);
}
