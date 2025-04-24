import java.util.Scanner;

public class IncomeTaxCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaxCalculator calculator = new TaxCalculator();
        
        while (true) {
            System.out.println("\n=== 个人所得税计算器 ===");
            System.out.println("请输入您的月收入（元）（输入0退出）：");
            
            // 获取用户输入
            double monthlyIncome;
            try {
                monthlyIncome = scanner.nextDouble();
                
                // 检查是否退出
                if (monthlyIncome == 0) {
                    System.out.println("感谢使用，再见！");
                    break;
                }
                
                // 验证输入是否有效
                if (monthlyIncome < 0) {
                    System.out.println("收入不能为负数，请重新输入！");
                    continue;
                }
                
                // 计算个税
                double tax = calculator.calculateTax(monthlyIncome);
                double afterTaxIncome = monthlyIncome - tax;
                
                // 输出结果
                System.out.println("\n=== 计算结果 ===");
                System.out.printf("月收入：%.2f 元\n", monthlyIncome);
                System.out.printf("应缴个税：%.2f 元\n", tax);
                System.out.printf("税后收入：%.2f 元\n", afterTaxIncome);
                System.out.println("------------------------");
                
            } catch (Exception e) {
                System.out.println("输入无效，请输入数字！");
                scanner.nextLine(); // 清除输入缓冲
            }
        }
        
        scanner.close();
    }
}

class TaxCalculator {
    // 个税起征点
    private static final double TAX_THRESHOLD = 5000;
    
    // 税率表
    private final double[] TAX_RATES = {0.03, 0.1, 0.2, 0.25, 0.3, 0.35, 0.45};
    
    // 应纳税所得额区间
    private final double[] TAX_LEVELS = {0, 3000, 12000, 25000, 35000, 55000, 80000};
    
    // 速算扣除数
    private final double[] QUICK_DEDUCTION = {0, 210, 1410, 2660, 4410, 7160, 15160};
    
    public double calculateTax(double monthlyIncome) {
        // 计算应纳税所得额
        double taxableIncome = monthlyIncome - TAX_THRESHOLD;
        
        // 如果应纳税所得额小于等于0，则不需要缴税
        if (taxableIncome <= 0) {
            return 0;
        }
        
        // 确定适用税率和速算扣除数
        int level = getTaxLevel(taxableIncome);
        
        // 计算税额
        return taxableIncome * TAX_RATES[level] - QUICK_DEDUCTION[level];
    }
    
    private int getTaxLevel(double taxableIncome) {
        for (int i = TAX_LEVELS.length - 1; i >= 0; i--) {
            if (taxableIncome > TAX_LEVELS[i]) {
                return i;
            }
        }
        return 0;
    }
} 