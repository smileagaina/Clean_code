package demo.ccc.com.service;


import demo.ccc.com.common.Solved;

import java.util.ArrayList;

/**
 * @description: 其他物资共享资金
 * @author: SHJ
 */
public class OtherSharedFund implements Solved {
    // 超额因子集合
    private ArrayList<ArrayList<Double>> O_OtherList = new ArrayList<>();
    // 共享资金集合
    private ArrayList<ArrayList<Double>> R_sharedOtherList = new ArrayList<>();
    // 其他物资超额因子
    private double overbookFactorOther = 1.5;
    // 其他物资最大共享资金
    private ArrayList<ArrayList<Double>> R_max_sharedOtherList = new ArrayList<>();
    /**
     * 按周数构造
     * @param weekAns
     */
    OtherSharedFund(int weekAns) {
        for (int i = 0; i < weekAns; i++) {
            O_OtherList.add(new ArrayList<Double>());
            R_sharedOtherList.add(new ArrayList<Double>());
            R_max_sharedOtherList.add(new ArrayList<Double>());
        }
    }

    /**
     * 某个城市某次t的分配其他物资超额因子O_Other(i, t)
     * @param curWeek
     * @param i
     * @param t
     * @return
     */
    @Override
    public void factor(int curWeek, int i, int t, double value) {
        setO_Other(curWeek, value);
    }

    public void factor(int curWeek, double value, int N_pool) {
        // 优化1
        for (int i = 0; i < N_pool; i++) {
            if (curWeek % 4 == i) {
                setO_Other(curWeek, overbookFactorOther);
            } else {
                setO_Other(curWeek, value);
            }
        }
    }

    @Override
    public void dedicatedFund(int curWeek, int i, int t, double value) {

    }

    @Override
    public void idealSharedFund(int curWeek, int i, int t, double value) {

    }

    @Override
    public void sharedFund(int curWeek, int i, int t, double value) {
        setShareFund(curWeek, value);
    }

    public void setO_Other(int curWeek, double value) {
        this.O_OtherList.get(curWeek).add(value);
    }

    public ArrayList<ArrayList<Double>> getO_Other() {
        return this.O_OtherList;
    }

    public double getO_Other(int curWeek, int i) {
        return this.O_OtherList.get(curWeek).get(i);
    }

    public void setShareFund(int curWeek, double value) {
        this.R_sharedOtherList.get(curWeek).add(value);
    }

    public double getShareFund(int curWeek, int i) {
        return this.R_sharedOtherList.get(curWeek).get(i);
    }

    public ArrayList<ArrayList<Double>> getShareFund() {
        return this.R_sharedOtherList;
    }

    public double getOverbookFactor() {
        return this.overbookFactorOther;
    }

    public void setR_max_sharedOther(int curWeek, double value) {
        this.R_max_sharedOtherList.get(curWeek).add(value);
    }

    public double getR_max_sharedOther(int curWeek, int i) {
        return this.R_max_sharedOtherList.get(curWeek).get(i);
    }

    public ArrayList<ArrayList<Double>> getR_max_sharedOther() {
        return this.R_max_sharedOtherList;
    }

}
