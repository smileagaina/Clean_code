package demo.ccc.com.service;

import java.io.*;
import java.util.*;

/**
 * @description: 试题解决框架类
 * @author: SHJ
 */
public class CleanCode {
    // 资金池最大值
    private double R_pool;
    // 资金池最小资金比例
    private double poolMinRate;
    // pool中资金服务的城市数量
    private int N_pool = 4;
    // 输入：所有城市需要的最大资金,一周，两周，或多周
    private ArrayList<ArrayList<Double>> R_maxList = new ArrayList<>();
    // 输入：所有城市实际消耗的口罩,一周，两周，或多周
    private ArrayList<ArrayList<Double>> load_SMList = new ArrayList<>();
    // 输入：所有城市实际消耗的消毒液,一周，两周，或多周
    private ArrayList<ArrayList<Double>> load_DisList = new ArrayList<>();
    // 输入：所有城市实际消耗的其他物资,一周，两周，或多周
    private ArrayList<ArrayList<Double>> load_otherList = new ArrayList<>();
    // 所有城市上报的最大资金需求，每周每个城市上报。
    private ArrayList<Double> R_sum_max = new ArrayList<>();
    // 周数
    private int weekAns;
    // 最小资金
    private MinFund minFund;
    // 城市口罩共享资金
    private SMSharedFund smSharedFund;
    // 消毒液共享资金
    private DisSharedFund disSharedFund;
    // 其他物资共享资金
    private OtherSharedFund otherSharedFund;
    // 每个城市的资金
    private ArrayList<ArrayList<Double>> cityFund = new ArrayList<>();
    // 取整后每个城市的资金
    private ArrayList<ArrayList<Integer>> finalCityFund = new ArrayList<>();

    /**
     * 一.输入
     */
    public void inputAndInit(File inputFile) {
        File file = inputFile;
        try {
            if (file.isFile() && file.exists()) {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(reader);
                int ans = 1;
                String firstLine = bufferedReader.readLine();
                R_pool = Double.valueOf(String.valueOf(firstLine.split(",")[0]));
                poolMinRate = Double.valueOf(String.valueOf(firstLine.split(",")[1]));
                String line1 = null;
                while ((line1 = bufferedReader.readLine()) != null) {
                    ans++;
                    ArrayList<Double> tempR_maxList = new ArrayList<>();
                    ArrayList<Double> tempLoad_SMList = new ArrayList<>();
                    ArrayList<Double> tempLoad_DisList = new ArrayList<>();
                    ArrayList<Double> tempLoad_otherList = new ArrayList<>();
                    // 每次读取4行
                    String line2 = bufferedReader.readLine();
                    String line3 = bufferedReader.readLine();
                    String line4 = bufferedReader.readLine();
                    String[] tempLine1 = line1.split(",");
                    String[] tempLine2 = line2.split(",");
                    String[] tempLine3 = line3.split(",");
                    String[] tempLine4 = line4.split(",");
                    tempR_maxList.add(Double.valueOf(tempLine1[0]));
                    tempR_maxList.add(Double.valueOf(tempLine2[0]));
                    tempR_maxList.add(Double.valueOf(tempLine3[0]));
                    tempR_maxList.add(Double.valueOf(tempLine4[0]));
                    // 加入最大资金集合
                    R_maxList.add(tempR_maxList);
                    tempLoad_SMList.add(Double.valueOf(tempLine1[1]));
                    tempLoad_SMList.add(Double.valueOf(tempLine2[1]));
                    tempLoad_SMList.add(Double.valueOf(tempLine3[1]));
                    tempLoad_SMList.add(Double.valueOf(tempLine4[1]));
                    // 加入实际口罩消耗集合
                    load_SMList.add(tempLoad_SMList);
                    tempLoad_DisList.add(Double.valueOf(tempLine1[2]));
                    tempLoad_DisList.add(Double.valueOf(tempLine2[2]));
                    tempLoad_DisList.add(Double.valueOf(tempLine3[2]));
                    tempLoad_DisList.add(Double.valueOf(tempLine4[2]));
                    // 加入实际消毒液消耗集合
                    load_DisList.add(tempLoad_DisList);
                    // 加入实际其他物资消耗集合
                    tempLoad_otherList.add(Double.valueOf(tempLine1[3]));
                    tempLoad_otherList.add(Double.valueOf(tempLine2[3]));
                    tempLoad_otherList.add(Double.valueOf(tempLine3[3]));
                    tempLoad_otherList.add(Double.valueOf(tempLine4[3]));
                    // 加入实际消毒液消耗集合
                    load_otherList.add(tempLoad_otherList);
                    // 空行
                    String spaceLine = bufferedReader.readLine();
                }
                // 周数
                weekAns = ans - 1;
                bufferedReader.close();
                reader.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件出错");
            e.printStackTrace();
        }
        // 计算所有城市上报的最大资金需求
        calculateSumR_max();
    }

    /**
     * 计算所有城市上报的最大资金需求，每周每个城市上报
     */
    public void calculateSumR_max() {
        for (int i = 0; i < weekAns; i++) {
            double sum = 0.0;
            ArrayList<Double> cas = R_maxList.get(i);
            for (int j = 0; j < cas.size(); j++) {
                sum += cas.get(j);
            }
            R_sum_max.add(sum);
        }
    }

    /**
     * 二.解决核心入口
     */
    public void solve() {
        // 实例化对象
        minFund = new MinFund(weekAns);
        smSharedFund = new SMSharedFund(weekAns);
        disSharedFund = new DisSharedFund(weekAns);
        otherSharedFund = new OtherSharedFund(weekAns);
        // 计算结果前先置空
        for (int t = 0; t < weekAns; t++) {
            cityFund.add(new ArrayList<Double>());
            finalCityFund.add(new ArrayList<Integer>());
        }
        // 遍历周的次数
        for (int t = 0; t < weekAns; t++) {
            // 给每个城市分配最小资金
            deliverMinFund(t);
            // 给每个城市分配口罩共享资金
            deliverSMSharedFund(t);
            // 给每个城市分配消毒液共享资金
            deliverDisSharedFund(t);
            // 给每个城市分配其他物资共享资金
            deliverOtherSharedFund(t);
            // 给每个城市算出分配给每个城市的总资金
            calculateEveryCityDeliverFund(t);
            // 总资金取整处理
            remandEveryCityDeliverFund(t);
        }
    }

    /**
     * 三.输出
     */
    public File output() {
        // 输出到文件case-1-output.txt
        File outputFile = new File("./testcases/my_output.txt");
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile))) {
            for (int t = 0; t < weekAns; t++) {
                ArrayList<Integer> tmpFinalList = finalCityFund.get(t);
                for (int i = 0; i < N_pool; i++) {
                    if (i == 0) writer.write(String.valueOf(tmpFinalList.get(i)));
                    else writer.write("," + tmpFinalList.get(i));
                }
                writer.write("\n");
            }
        } catch (Exception e) {
            System.out.println("写入文件失败");
            e.printStackTrace();
        }
        return outputFile;
    }

    /**
     * 分配算法1：给每个城市分配最小资金
     * @param t
     */
    public void deliverMinFund(int t) {
        int curWeek = t;
        for (int i = 0; i < N_pool; i++) {
            double R_min_i_t = getR_min(curWeek, i, t);
            // 当前周的第i个城市的最小资金
            minFund.setMinFundList(curWeek, i, R_min_i_t);
        }
    }

    /**
     * 分配算法2：给每个城市分配口罩共享资金
     */
    public void deliverSMSharedFund(int t) {
        // 每周分配一次，所有第一周是第一次计算，第二周是第二次计算。
        double lastR_sum_i = 0.0;
        int curWeek = t;
        double sumR_idealShareSM = 0.0;
        for (int i = 0; i < N_pool; i++) {
            // R_sum(i, t - 1) 等于上一周计算的，第i个城市,分配给第i个城市的总资金
            if (t >= 1) lastR_sum_i = finalCityFund.get(t - 1).get(i);
            // 计算某个城市i某次t分配口罩超额因子
            double O_SM_i_t = O_SM(curWeek, i, t, (t >= 1 ? smSharedFund.getO_SM().get(t - 1).get(i) : 1), lastR_sum_i);
            smSharedFund.factor(curWeek, i, t, O_SM_i_t);
            // 计算某个城市i某次t分配口罩专有资金
            double R_dedicate_SM_i_t = R_dedicated_SM(curWeek, i, t, O_SM_i_t);
            smSharedFund.dedicatedFund(curWeek, i, t, R_dedicate_SM_i_t);
            // 计算某个城市i某次t分配口罩理想共享资金
            double R_idealShared_SM_i_t = R_idealSharedSM(curWeek, i, t, O_SM_i_t, R_dedicate_SM_i_t);
            smSharedFund.idealSharedFund(curWeek, i, t, R_idealShared_SM_i_t);
            sumR_idealShareSM += R_idealShared_SM_i_t;
        }
        // 最后计算共享资金
        for (int i = 0; i < N_pool; i++) {
            // 计算某个城市i某次t分配口罩共享资金
            double R_sharedSM = R_sharedSM(curWeek, i, t, sumR_idealShareSM, smSharedFund.getIdealShareFund(curWeek, i));
            smSharedFund.sharedFund(curWeek, i, t, R_sharedSM);
        }
    }

    /**
     * 分配算法3：给每个城市分配消毒液共享资金
     */
    public void deliverDisSharedFund(int t) {
        // 每周分配一次，所有第一周是第一次计算，第二周是第二次计算。
        double lastR_sum_i = 0;
        int curWeek = t;
        double sumR_idealShareDis = 0.0;
        for (int i = 0; i < N_pool; i++) {
            // R_sum(i, t - 1) 等于上一周计算的，第i个城市
            if (t >= 1) lastR_sum_i = finalCityFund.get(t - 1).get(i);
            // 计算某个城市i某次t分配消毒液超额因子
            double O_Dis_i_t = O_Dis(curWeek, i, t, (t >= 1 ? disSharedFund.getO_Dis().get(t - 1).get(i) : 1), lastR_sum_i);
            disSharedFund.factor(curWeek, i, t, O_Dis_i_t);
            // 计算某个城市i某次t分配消毒液专有资金
            double R_dedicate_Dis_i_t = R_dedicated_Dis(curWeek, i, t, O_Dis_i_t);
            disSharedFund.dedicatedFund(curWeek, i, t, R_dedicate_Dis_i_t);
            // 计算某个城市i某次t分配消毒液理想共享资金
            double R_idealShared_Dis_i_t = R_idealSharedDis(curWeek, i, t, O_Dis_i_t, R_dedicate_Dis_i_t);
            disSharedFund.idealSharedFund(curWeek, i, t, R_idealShared_Dis_i_t);
            sumR_idealShareDis += R_idealShared_Dis_i_t;
        }
        // 最后计算共享资金
        for (int i = 0; i < N_pool; i++) {
            // 计算某个城市i某次t分配消毒液共享资金
            double R_sharedDis = R_sharedDis(curWeek, i, t, sumR_idealShareDis, disSharedFund.getIdealShareFund(curWeek, i));
            disSharedFund.sharedFund(curWeek, i, t, R_sharedDis);
        }
    }

    /**
     * 分配算法4：给每个城市分配其他物资共享资金
     */
    public void deliverOtherSharedFund(int t) {
        // 每周分配一次，所有第一周是第一次计算，第二周是第二次计算。
        int curWeek = t;
        for (int i = 0; i < N_pool; i++) {
            // 计算某个城市i某次t分配其他物资最大共享资金
            double R_max_sharedOther = R_max_sharedOther(curWeek, i, t);
            otherSharedFund.setR_max_sharedOther(curWeek, R_max_sharedOther);
        }
        // 计算所有城市某次t分配其他物资超额因子
        otherSharedFund.factor(curWeek, 1, N_pool);
        // 最后计算共享资金
        for (int i = 0; i < N_pool; i++) {
            // 计算某个城市i某次t分配其他物资共享资金
            double R_sharedOther = R_sharedOther(curWeek, i, t);
            otherSharedFund.sharedFund(curWeek, i, t, R_sharedOther);
        }
    }

    /**
     * 分配算法5：给每个城市算出分配给每个城市的总资金
     */
    public void calculateEveryCityDeliverFund(int t) {
        // 每周分配一次，所有第一周是第一次计算，第二周是第二次计算。
        int curWeek = t;
        for (int i = 0; i < N_pool; i++) {
            double R_sum_i_t = minFund.getMinFundList().get(curWeek).get(i) + smSharedFund.getShareFund(curWeek, i) +
                    disSharedFund.getShareFund(curWeek, i) + otherSharedFund.getShareFund(curWeek, i);
            cityFund.get(curWeek).add(R_sum_i_t);
        }
    }

    /**
     * 分配算法6：总资金取整处理
     */
    public void remandEveryCityDeliverFund(int t) {
        // 每周分配一次，所有第一周是第一次计算，第二周是第二次计算。
        int curWeek = t;
        // 1.把分到城市分配到的总资金，先取其整数部分。
        ArrayList<Integer> R_sumIntPartList = new ArrayList<>();
        int sumR_sumIntPartList = 0;
        for (int i = 0; i < N_pool; i++) {
            double tmpValue = cityFund.get(curWeek).get(i);
            R_sumIntPartList.add((int) tmpValue);
            sumR_sumIntPartList += (int) tmpValue;
        }
        // 2.如果每个城市只分配整数部分，资金池中可能还有剩余的资金
        double R_pool_remand = R_pool - sumR_sumIntPartList;
        /**
         * 3.按每个城市的上报的编号（输入中每个城市从前到后分别为0，1，2，3）轮询把剩余的资金分配给城市；
         * 但要确保每个城市不能超过最大值R_max(i, t)。
         */
        for (int k = 0; k < 4 && R_pool_remand > 0; k++) {
            int R_sum_IntPart_k_t = R_sumIntPartList.get(k);
            double R_sum_k_t = Math.min(R_sum_IntPart_k_t + R_pool_remand, R_maxList.get(curWeek).get(k));
            this.cityFund.get(curWeek).set(k, R_sum_k_t);
            R_pool_remand = R_pool_remand - (R_sum_k_t - R_sum_IntPart_k_t);
        }
        for (int i = 0; i < N_pool; i++) {
            double tmpValue = this.cityFund.get(curWeek).get(i);
            this.finalCityFund.get(curWeek).add((int)(tmpValue));
        }
    }


    /**
     * 1.给每个城市分配最小资金
     * R_min(i, t): 某个城市i某次t分配最小资金R_min(i, t)
     * @param curWeek 周次，即计算次数
     * @param i
     * @param t
     * @return
     */
    public double getR_min(int curWeek, int i, int t) {
        double result = Math.min(Math.ceil(R_pool*poolMinRate/100.0*R_maxList.get(curWeek).get(i)/R_sum_max.get(curWeek)),
                R_maxList.get(curWeek).get(i));
        return result;
    }

    /**
     * 2.分配口罩共享资金
     * (1)口罩超额因子
     * O_SM(i, t): 某个城市i某次t分配口罩超额因子
     * @param curWeek
     * @param i
     * @param t
     * @param lastO_SM 上次计算口罩资金使用的超额因子
     * @param lastR_sum_i 上次计算分给城市i的总资金，初始值为0
     * @return
     */
    public double O_SM(int curWeek, int i, int t, double lastO_SM, double lastR_sum_i) {
        double result = 0.0;
        if (load_SMList.get(curWeek).get(i)*lastO_SM < lastR_sum_i) {
            result = 1.0;
        } else {
            result = smSharedFund.getOverbookFactor();
        }
        return result;
    }

    /**
     * 2.分配口罩共享资金
     * (2)口罩专有资金
     * R_dedicated_SM(i, t): 某个城市i某次t分配专有资金
     * @param curWeek
     * @param i
     * @param t
     * @param O_SM_i_t 本次计算口罩资金使用的超额因子
     * @return
     */
    public double R_dedicated_SM(int curWeek, int i, int t, double O_SM_i_t) {
        double result = Math.min(O_SM_i_t * load_SMList.get(curWeek).get(i),
                minFund.getMinFundList().get(curWeek).get(i));
        return result;
    }

    /**
     * 2.分配口罩共享资金
     * (3)口罩理想共享资金
     * R_idealSharedSM(i, t): 某个城市i某次t分配口罩理想共享资金
     * @param curWeek
     * @param i
     * @param t
     * @param O_SM_i_t 本次计算口罩资金使用的超额因子
     * @param R_dedicate_SM_i_t 口罩专有资金
     * @return
     */
    public double R_idealSharedSM(int curWeek, int i, int t, double O_SM_i_t, double R_dedicate_SM_i_t) {
        double result = Math.max(Math.min(smSharedFund.getO_SM(curWeek, i) *
                        load_SMList.get(curWeek).get(i) - smSharedFund.getDedicated_SM(curWeek, i),
                (R_maxList.get(curWeek).get(i) - minFund.getMinFundList().get(curWeek).get(i))), 0);
        // 因为有减法，保护一下理想共享资金不能为负值
        if (result < 0) result = 0.0;
        return result;
    }

    /**
     * 2.分配口罩共享资金
     * (4)口罩共享资金
     * R_sharedSM(i, t): 某个城市i某次t分配口罩共享资金
     * @param curWeek
     * @param i
     * @param t
     * @param sumR_idealShareSM 所有城市的口罩理想共享资金之和
     * @param iR_idealShareSM 口罩理想共享资金
     * @return
     */
    public double R_sharedSM(int curWeek, int i, int t, double sumR_idealShareSM, double iR_idealShareSM) {
        double sumR_min = 0.0;
        for (int j = 0; j < N_pool; j++) {
            sumR_min += minFund.getMinFundList().get(curWeek).get(j);
        }
        double R_SM_pool_t = R_pool - sumR_min;
        double result = 0.0;
        if (sumR_idealShareSM - 0.0 < 1e-7) {
            result = Math.min(iR_idealShareSM, R_SM_pool_t);
        } else {
            result = Math.min(iR_idealShareSM, iR_idealShareSM*1.0*R_SM_pool_t/sumR_idealShareSM);
        }
        // 共享资金不能超过自己的理想共享资金
        if (result > smSharedFund.getIdealShareFund(curWeek, i))  result = smSharedFund.getIdealShareFund(curWeek, i);
        return result;
    }

    /**
     * 3.分配消毒液共享资金
     * (1)消毒液超额因子
     * O_Dis(i, t): 某个城市i某次t分配消毒液超额因子
     * @param curWeek
     * @param i
     * @param t
     * @param lastO_Dis 上一次计算时的超额因子
     * @param lastR_sum_i 上一次计算分给城市i的总资金，初始值为0
     * @return
     */
    public double O_Dis(int curWeek, int i, int t, double lastO_Dis, double lastR_sum_i) {
        double result = 0.0;
        if (load_DisList.get(curWeek).get(i)*lastO_Dis < lastR_sum_i) {
            result = 1.0;
        } else {
            result = disSharedFund.getOverbookFactor();
        }
        return result;
    }

    /**
     * 3.分配消毒液共享资金
     * (2)消毒液专有资金
     * R_dedicated_Dis(i, t): 某个城市i某次t分配消毒液专有资金
     * 其中：R_min_Dis(i, t) = R_min(i, t) - R_dedicated_Dis(i, t)
     * @param curWeek
     * @param i
     * @param t
     * @param O_Dis_i_t 消毒液资金使用的超额因子
     * @return
     */
    public double R_dedicated_Dis(int curWeek, int i, int t, double O_Dis_i_t) {
        double R_min_dis_i_t = minFund.getMinFundList().get(curWeek).get(i) - smSharedFund.getDedicated_SM(curWeek, i);
        double result = Math.min(O_Dis_i_t * load_DisList.get(curWeek).get(i),
                R_min_dis_i_t);
        return result;
    }

    /**
     * 3.分配消毒液共享资金
     * (3)消毒液理想共享资金
     * R_idealSharedDis(i, t): 某个城市i某次t分配消毒液理想共享资金
     * @param curWeek
     * @param i
     * @param t
     * @param O_Dis_i_t 消毒液资金使用的超额因子
     * @param R_dedicate_Dis_i_t 消毒液专有资金
     * @return
     */
    public double R_idealSharedDis(int curWeek, int i, int t, double O_Dis_i_t, double R_dedicate_Dis_i_t) {
        double result = Math.max(Math.min(O_Dis_i_t * load_DisList.get(curWeek).get(i) -
                        disSharedFund.getDedicated_Dis(curWeek, i),
                (R_maxList.get(curWeek).get(i) - minFund.getMinFundList().get(curWeek).get(i) -
                        smSharedFund.getShareFund(curWeek, i))), 0.0);
        // 因为有减法，保护一下理想共享资金不能为负值
        if (result < 0) result = 0.0;
        return result;
    }

    /**
     * 3.分配消毒液共享资金
     * (4)消毒液共享资金
     * R_sharedDis(i, t): 某个城市i某次t分配消毒液共享资金
     * @param curWeek
     * @param i
     * @param t
     * @param sumR_idealShareDis 所有城市的消毒液理想共享资金之和
     * @param iR_idealShareDis 消毒液理想共享资金
     * @return
     */
    public double R_sharedDis(int curWeek, int i, int t, double sumR_idealShareDis, double iR_idealShareDis) {
        double sumR_min = 0.0;
        for (int j = 0; j < N_pool; j++) {
            sumR_min += minFund.getMinFundList().get(curWeek).get(j);
        }
        double R_SM_pool_t = R_pool - sumR_min;
        double sumR_sharedSM = 0.0;
        for (int j = 0; j < N_pool; j++) {
            sumR_sharedSM += smSharedFund.getShareFund(curWeek, j);
        }
        double R_Dis_pool = R_SM_pool_t - sumR_sharedSM;
        double result = 0.0;
        if (sumR_idealShareDis - 0.0 < 1e-7) {
            result = Math.min(iR_idealShareDis, R_SM_pool_t);
        } else {
            result = Math.min(iR_idealShareDis, iR_idealShareDis*1.0*R_Dis_pool/sumR_idealShareDis);
        }
        // 共享资金不能超过自己的理想共享资金
        if (result > disSharedFund.getIdealShareFund(curWeek, i))  result = disSharedFund.getIdealShareFund(curWeek, i);
        return result;
    }

    /**
     * 4.分配其他物资共享资金
     * (1)其他物资最大共享资金
     * R_max_sharedOther(i, t): 某个城市i某次t分配其他物资最大共享资金
     * @param curWeek
     * @param i
     * @param t
     * @return
     */
    public double R_max_sharedOther(int curWeek, int i, int t) {
        double result = R_maxList.get(curWeek).get(i) -
                minFund.getMinFundList().get(curWeek).get(i) -
                smSharedFund.getShareFund(curWeek, i) -
                disSharedFund.getShareFund(curWeek, i);
        return result;
    }

    /**
     * 4.分配其他物资共享资金
     * (2)其他物资超额因子
     * O_other(i, t): 某个城市i某次t分配其他物资超额因子
     * @param i
     * @param t
     * @return
     */
    public double O_other(int i, int t) {
        double result = 0.0;
        return result;
    }

    /**
     * 4.分配其他物资共享资金
     * (3)其他物资共享资金
     * R_sharedOther(i, t): 某个城市i某次t分配其他物资共享资金
     * @param curWeek
     * @param i
     * @param t
     * @return 其他物资共享资金
     */
    public double R_sharedOther(int curWeek, int i, int t) {
        double sumR_min = 0.0;
        for (int j = 0; j < N_pool; j++) {
            sumR_min += minFund.getMinFundList().get(curWeek).get(j);
        }
        double R_SM_pool_t = R_pool - sumR_min;
        double sumR_sharedSM = 0.0;
        double sumR_sharedDis = 0.0;
        double sumLoadOther_O_Other = 0.0;
        for (int j = 0; j < N_pool; j++) {
            sumR_sharedSM += smSharedFund.getShareFund(curWeek, j);
            sumR_sharedDis += disSharedFund.getShareFund(curWeek, j);
            sumLoadOther_O_Other += (load_otherList.get(curWeek).get(j) * otherSharedFund.getO_Other(curWeek, j));
        }
        double R_Dis_pool = R_SM_pool_t - sumR_sharedSM;
        double R_Other_pool_i_t = R_Dis_pool - sumR_sharedDis;
        double result = Math.min(otherSharedFund.getR_max_sharedOther(curWeek, i),
                load_otherList.get(curWeek).get(i) * otherSharedFund.getO_Other(curWeek, i) *
                        R_Other_pool_i_t / sumLoadOther_O_Other);
        return result;
    }

    /**
     * 5.分配给每个城市的总资金
     * (1)计算总资金：分配给城市的总资金 = 最小资金 + 口罩共享资金 + 消毒液共享资金 + 其他物资共享资金
     * R_sum(i, t) = R_min(i, t) + R_sharedSM(i, t) + R_sharedDis(i, t) + R_sharedOther(i, t)
     * @param i
     * @param t
     * @return
     */
    public double R_sum(int i, int t) {
        double result = 0.0;
        return result;
    }

    /**
     * 6.总资金取整算法
     * @param i
     * @param t
     * @return
     */
    public double R_pool_remand(int i, int t) {
        double result = 0.0;
        return result;
    }


}
