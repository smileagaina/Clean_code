package demo.ccc.com.service;


import demo.ccc.com.common.Solved;

import java.util.ArrayList;

/**
 * @description: 消毒液共享资金
 * @author: SHJ
 */
public class DisSharedFund implements Solved {
    // 超额因子集合
    private ArrayList<ArrayList<Double>> O_DisList = new ArrayList<>();
    // 专有资金集合
    private ArrayList<ArrayList<Double>>  R_dedicated_DisList = new ArrayList<>();
    // 理想共享资金集合
    private ArrayList<ArrayList<Double>> R_idealSharedDisList = new ArrayList<>();
    // 共享资金集合
    private ArrayList<ArrayList<Double>> R_sharedDisList = new ArrayList<>();
    // 消毒液超额因子
    private double overbookFactorDis = 1.2;
    /**
     * 按周数构造
     * @param weekAns
     */
    DisSharedFund(int weekAns) {
        for (int i = 0; i < weekAns; i++) {
            O_DisList.add(new ArrayList<Double>());
            R_dedicated_DisList.add(new ArrayList<Double>());
            R_idealSharedDisList.add(new ArrayList<Double>());
            R_sharedDisList.add(new ArrayList<Double>());
        }
    }

    /**
     * 某个城市某次t的分配消毒液超额因子O_Dis(i, t)
     * @param curWeek
     * @param i
     * @param t
     * @return
     */
    @Override
    public void factor(int curWeek, int i, int t, double value) {
        setO_Dis(curWeek, value);
    }

    @Override
    public void dedicatedFund(int curWeek, int i, int t, double value) {
        setDedicated_Dis(curWeek, value);
    }

    @Override
    public void idealSharedFund(int curWeek, int i, int t, double value) {
        setIdealShareFund(curWeek, value);
    }

    @Override
    public void sharedFund(int curWeek, int i, int t, double value) {
        setShareFund(curWeek, value);
    }

    public void setO_Dis(int curWeek, double value) {
        this.O_DisList.get(curWeek).add(value);
    }

    public ArrayList<ArrayList<Double>> getO_Dis() {
        return this.O_DisList;
    }

    public double getO_Dis(int curWeek, int i) {
        return this.O_DisList.get(curWeek).get(i);
    }

    public void setDedicated_Dis(int curWeek, double value) {
        this.R_dedicated_DisList.get(curWeek).add(value);
    }

    public double getDedicated_Dis(int curWeek, int i) {
        return this.R_dedicated_DisList.get(curWeek).get(i);
    }

    public ArrayList<ArrayList<Double>> getDedicated_Dis() {
        return this.R_dedicated_DisList;
    }

    public void setIdealShareFund(int curWeek, double value) {
        this.R_idealSharedDisList.get(curWeek).add(value);
    }

    public double getIdealShareFund(int curWeek, int i) {
        return this.R_idealSharedDisList.get(curWeek).get(i);
    }

    public ArrayList<ArrayList<Double>> getIdealShareFund() {
        return this.R_idealSharedDisList;
    }

    public void setShareFund(int curWeek, double value) {
        this.R_sharedDisList.get(curWeek).add(value);
    }

    public double getShareFund(int curWeek, int i) {
        return this.R_sharedDisList.get(curWeek).get(i);
    }

    public ArrayList<ArrayList<Double>> getShareFund() {
        return this.R_sharedDisList;
    }

    public double getOverbookFactor() {
        return this.overbookFactorDis;
    }
}
