package demo.ccc.com.service;

import java.util.ArrayList;

/**
 * @description: 最小资金
 * @author: SHJ
 */
public class MinFund {
    private ArrayList<ArrayList<Double>> minFundList = new ArrayList<>();

    /**
     * 按周数构造
     * @param weekAns
     */
    MinFund(int weekAns) {
        for (int i = 0; i < weekAns; i++) {
            this.minFundList.add(new ArrayList<Double>());
        }
    }

    /**
     * 计算最小资金
     * @param curWeek
     * @param i
     * @param R_min
     */
    public void setMinFundList(int curWeek, int i, double R_min) {
        this.minFundList.get(curWeek).add(R_min);
    }

    /**
     * 返回最小资金
     * @return
     */
    public ArrayList<ArrayList<Double>> getMinFundList() {
        return this.minFundList;
    }

}
