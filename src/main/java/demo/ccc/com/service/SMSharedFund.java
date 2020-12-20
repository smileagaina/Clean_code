package demo.ccc.com.service;


import demo.ccc.com.common.Solved;

import java.util.ArrayList;

/**
 * @description: 口罩共享资金
 * @author: SHJ
 */
public class SMSharedFund implements Solved {
    // 超额因子集合
    private ArrayList<ArrayList<Double>> O_SMList = new ArrayList<>();
    // 专有资金集合
    private ArrayList<ArrayList<Double>>  R_dedicated_SMList = new ArrayList<>();
    // 理想共享资金集合
    private ArrayList<ArrayList<Double>> R_idealSharedSMList = new ArrayList<>();
    // 共享资金集合
    private ArrayList<ArrayList<Double>> R_sharedSMList = new ArrayList<>();
    // 口罩超额因子
    private double overbookFactorSM = 1.5;
    /**
     * 按周数构造
     * @param weekAns
     */
    SMSharedFund(int weekAns) {
        for (int i = 0; i < weekAns; i++) {
            O_SMList.add(new ArrayList<Double>());
            R_dedicated_SMList.add(new ArrayList<Double>());
            R_idealSharedSMList.add(new ArrayList<Double>());
            R_sharedSMList.add(new ArrayList<Double>());
        }
    }

    /**
     * 某个城市某次t的分配口罩超额因子O_SM(i, t)
     * @param curWeek
     * @param i
     * @param t
     * @return
     */
    @Override
    public void factor(int curWeek, int i, int t, double value) {
        setO_SM(curWeek, value);
    }

    @Override
    public void dedicatedFund(int curWeek, int i, int t, double value) {
        setDedicated_SM(curWeek, value);
    }

    @Override
    public void idealSharedFund(int curWeek, int i, int t, double value) {
        setIdealShareFund(curWeek, value);
    }

    @Override
    public void sharedFund(int curWeek, int i, int t, double value) {
        setShareFund(curWeek, value);
    }

    public void setO_SM(int curWeek, double value) {
        this.O_SMList.get(curWeek).add(value);
    }

    public ArrayList<ArrayList<Double>> getO_SM() {
        return this.O_SMList;
    }

    public double getO_SM(int curWeek, int i) {
        return this.O_SMList.get(curWeek).get(i);
    }

    public void setDedicated_SM(int curWeek, double value) {
        this.R_dedicated_SMList.get(curWeek).add(value);
    }

    public double getDedicated_SM(int curWeek, int i) {
        return this.R_dedicated_SMList.get(curWeek).get(i);
    }

    public ArrayList<ArrayList<Double>> getDedicated_SM() {
        return this.R_dedicated_SMList;
    }

    public void setIdealShareFund(int curWeek, double value) {
        this.R_idealSharedSMList.get(curWeek).add(value);
    }

    public double getIdealShareFund(int curWeek, int i) {
        return this.R_idealSharedSMList.get(curWeek).get(i);
    }

    public ArrayList<ArrayList<Double>> getIdealShareFund() {
        return this.R_idealSharedSMList;
    }

    public void setShareFund(int curWeek, double value) {
        this.R_sharedSMList.get(curWeek).add(value);
    }

    public double getShareFund(int curWeek, int i) {
        return this.R_sharedSMList.get(curWeek).get(i);
    }

    public ArrayList<ArrayList<Double>> getShareFund() {
        return this.R_sharedSMList;
    }

    public double getOverbookFactor() {
        return this.overbookFactorSM;
    }

}
