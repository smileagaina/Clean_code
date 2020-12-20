package demo.ccc.com;

import demo.ccc.com.service.CleanCode;

import java.io.File;

/**
 * Please add your function!
 * Hello world!
 *
 */
public class App {
	public File yourFunction(File inputFile) {
		/**
		 * your function
		 */
		CleanCode cleanCode = new CleanCode();
		// 输入和初始化
		cleanCode.inputAndInit(inputFile);
		// 解决方法核心入口
		cleanCode.solve();
		// 输出
		File outputFile = cleanCode.output();
		return outputFile;
	}
}
