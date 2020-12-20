package demo.ccc.com.common;

/**
 * @description: 公共方法接口类
 * @author: SHJ
 */
public interface Solved {
    /**
     * 计算给每个城市分配的超额因子
     * @param curWeek
     * @param i
     *git branch -M main
     * @param t
     * @param value
     */
    public void factor(int curWeek, int i, int t, double value);

    /**
     * 计算给每个城市分配的专有资金
     * @param curWeek
     * @param i
     * @param t
     * @param value
     * @return 专有资金
     */
    public void dedicatedFund(int curWeek, int i, int t, double value);

    /**
     * 计算给每个城市分配的理想共享资金
     * @param curWeek
     * @param i
     * @param t
     * @param value
     * @return 理想共享资金
     */
    public void idealSharedFund(int curWeek, int i, int t, double value);

    /**
     * 计算给每个城市分配的共享资金
     * @param curWeek
     * @param i
     * @param t
     * @param value
     * @return 共享资金
     */
    public void sharedFund(int curWeek, int i, int t, double value);
}
