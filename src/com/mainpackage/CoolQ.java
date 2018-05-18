package com.mainpackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.sobte.cqp.jcq.entity.Anonymous;
import com.sobte.cqp.jcq.entity.CQCode;
import com.sobte.cqp.jcq.entity.GroupFile;
import com.sobte.cqp.jcq.entity.ICQVer;
import com.sobte.cqp.jcq.entity.IMsg;
import com.sobte.cqp.jcq.entity.IRequest;
import com.sobte.cqp.jcq.event.JcqAppAbstract;

/**
 * 本文件是JCQ插件的主类<br>
 * <br>
 * 
 * 注意修改json中的class来加载主类，如不设置则利用appid加载，最后一个单词自动大写查找<br>
 * 例：appid(com.example.demo) 则加载类 com.example.Demo<br>
 * 文档地址： https://gitee.com/Sobte/JCQ-CoolQ <br>
 * 帖子：https://cqp.cc/t/37318 <br>
 * 辅助开发变量: {@link JcqAppAbstract#CQ CQ}({@link com.sobte.cqp.jcq.entity.CoolQ 酷Q核心操作类}),
 * 			 {@link JcqAppAbstract#CC CC}({@link com.sobte.cqp.jcq.entity.CQCode 酷Q码操作类}),
 * 			   具体功能可以查看文档
 */
public class CoolQ extends JcqAppAbstract implements ICQVer, IMsg, IRequest {

	/**
	 * 打包后将不会调用 请不要在此事件中写其他代码
	 * 
	 * @return 返回应用的ApiVer、Appid
	 */
	public String appInfo() {
		// 应用AppID,规则见 http://d.cqp.me/Pro/开发/基础信息#appid
		String AppID = "com.mainpackage.CoolQ";// 记住编译后的文件和json也要使用appid做文件名
		/**
		 * 本函数【禁止】处理其他任何代码，以免发生异常情况。
		 * 如需执行初始化代码请在 startup 事件中执行（Type=1001）。
		 */
		return CQAPIVER + "," + AppID;
	}

	/**
	 * 酷Q启动 (Type=1001)<br>
	 * 本方法会在酷Q【主线程】中被调用。<br>
	 * 请在这里执行插件初始化代码。<br>
	 * 请务必尽快返回本子程序，否则会卡住其他插件以及主程序的加载。
	 * 
	 * @return 请固定返回0
	 */
	public int startup() {
		// 获取应用数据目录(无需储存数据时，请将此行注释)
		String appDirectory = CQ.getAppDirectory();
		// 返回如：D:\CoolQ\app\com.sobte.cqp.jcq\app\com.example.demo\
		// 应用的所有数据、配置【必须】存放于此目录，避免给用户带来困扰。
		return 0;
	}

	/**
	 * 酷Q退出 (Type=1002)<br>
	 * 本方法会在酷Q【主线程】中被调用。<br>
	 * 无论本应用是否被启用，本函数都会在酷Q退出前执行一次，请在这里执行插件关闭代码。
	 * 
	 * @return 请固定返回0，返回后酷Q将很快关闭，请不要再通过线程等方式执行其他代码。
	 */
	public int exit() {
		return 0;
	}

	/**
	 * 应用已被启用 (Type=1003)<br>
	 * 当应用被启用后，将收到此事件。<br>
	 * 如果酷Q载入时应用已被启用，则在 {@link #startup startup}(Type=1001,酷Q启动) 被调用后，本函数也将被调用一次。<br>
	 * 如非必要，不建议在这里加载窗口。
	 * 
	 * @return 请固定返回0。
	 */
	public int enable() {
		enable = true;
		return 0;
	}

	/**
	 * 应用将被停用 (Type=1004)<br>
	 * 当应用被停用前，将收到此事件。<br>
	 * 如果酷Q载入时应用已被停用，则本函数【不会】被调用。<br>
	 * 无论本应用是否被启用，酷Q关闭前本函数都【不会】被调用。
	 * 
	 * @return 请固定返回0。
	 */
	public int disable() {
		enable = false;
		return 0;
	}

	/**
	 * 私聊消息 (Type=21)<br>
	 * 本方法会在酷Q【线程】中被调用。<br>
	 * 
	 * @param subType
	 *            子类型，11/来自好友 1/来自在线状态 2/来自群 3/来自讨论组
	 * @param msgId
	 *            消息ID
	 * @param fromQQ
	 *            来源QQ
	 * @param msg
	 *            消息内容
	 * @param font
	 *            字体
	 * @return 返回值*不能*直接返回文本 如果要回复消息，请调用api发送<br>
	 *         这里 返回  {@link IMsg#MSG_INTERCEPT MSG_INTERCEPT} - 截断本条消息，不再继续处理<br>
	 *         注意：应用优先级设置为"最高"(10000)时，不得使用本返回值<br>
	 *         如果不回复消息，交由之后的应用/过滤器处理，这里 返回  {@link IMsg#MSG_IGNORE MSG_IGNORE} - 忽略本条消息
	 */
	public int privateMsg(int subType, int msgId, long fromQQ, String msg, int font) {
		// 这里处理消息
		
		//编译器部分，只处理长度>3的消息
		if (msg.length() > 3) {
			try {
				String output = processMsg(subType, msgId, fromQQ, msg, font);
				if (output.length() >= 1) { //如果有打印值
					CQ.sendPrivateMsg(fromQQ, CC.at(fromQQ) + "\n" + output);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//自动谱曲部分 "#m;"标识符是否存在
		if (CQCode.decode(msg).indexOf("#m;") != -1) {
			//是否有内容
			if (CQCode.decode(msg).length() > 3) {
				try {
					//写入rhythm<random_tag>.txt文件
					String tag = randomAlphaNumeric(10);
					String musictag = randomAlphaNumeric(10);
					BufferedWriter writer = new BufferedWriter(new FileWriter("rhythm" + tag + ".txt"));
					writer.write(CQCode.decode(msg));
					writer.close();
					//执行midi生成并转换
					//in Python: 
					//sys.argv[0] = "music.py"
					//sys.argv[1] = tag
					//sys.argv[2] = musictag
					execCmd("Python music.py " + tag + " " + musictag);
					//发送wmv文件
					CQ.sendPrivateMsg(fromQQ, "[CQ:record,file=" + musictag + ".wav]");
					//删除临时文件
					File file1 = new File("rhythm" + tag + ".txt");
					File file2 = new File("data/record/" + musictag + ".wav");
					file1.delete();
					file2.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//帮助部分 - 总览
		if (CQCode.decode(msg).indexOf("#help;") != -1) {
			String output = "您好，这里是御坂10032号。目前御坂网络已经实装了以下功能：\n\n"
					+ "1. Python编译器(#p;)\n"
					+ "2. Java编译器(#j;)\n"
					+ "3. CommonLisp编译器(#l;)\n"
					+ "4. 自动音乐生成器(#m;)(请输入#mhelp;来获取音乐生成指南)";
			CQ.sendPrivateMsg(fromQQ, CC.at(fromQQ) + "\n" + output);
		}
		
		//帮助部分 - 音乐生成器
		if (CQCode.decode(msg).indexOf("#mhelp;") != -1) {
			String output = ""
					+ "您好，这里是御坂10032号。这里是音乐生成器的使用指南：\n"
					+ "\n"
					+ "请按照以下范例来生成任意音乐\n"
					+ "#m; ←第一行必须是这个\n"
					+ "0,60,1 ←[从第几拍开始,第几个键(钢琴),持续几拍] 下同\n"
					+ "1,62,1 ←请注意逗号为英文逗号\n"
					+ "2,64,1\n"
					+ "\n"
					+ "参考: 60为中音Do,61为升Do,62为Re,以此类推\n"
					+ "\n"
					+ "备注：过于频繁地发送音乐信息会导致生成失败";
			CQ.sendPrivateMsg(fromQQ, CC.at(fromQQ) + "\n" + output);
		}
		
		//自定义判定 范例
		/*
		String decodedMsg = CQCode.decode(msg);
		if (decodedMsg.equals("发送图片")) {
			CQ.sendPrivateMsg(fromQQ, CC.at(fromQQ) + "\n" + "[CQ:image,file=testimg.jpg]");
		}
		if (decodedMsg.equals("发送音乐")) {
			CQ.sendPrivateMsg(fromQQ, CC.at(fromQQ) + "\n" + "[CQ:record,file=testsound.mp3]");
		}
		if (decodedMsg.equals("发送MIDI")) {
			CQ.sendPrivateMsg(fromQQ, CC.at(fromQQ) + "\n" + "[CQ:record,file=testMIDI.mid]");
		}
		*/
		
		return MSG_IGNORE;
	}

	/**
	 * 群消息 (Type=2)<br>
	 * 本方法会在酷Q【线程】中被调用。<br>
	 *
	 * @param subType
	 *            子类型，目前固定为1
	 * @param msgId
	 *            消息ID
	 * @param fromGroup
	 *            来源群号
	 * @param fromQQ
	 *            来源QQ号
	 * @param fromAnonymous
	 *            来源匿名者
	 * @param msg
	 *            消息内容
	 * @param font
	 *            字体
	 * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
	 */
	public int groupMsg(int subType, int msgId, long fromGroup, long fromQQ, String fromAnonymous, String msg,
			int font) {
		// 如果消息来自匿名者
        if (fromQQ == 80000000L && !fromAnonymous.equals("")) {
            // 将匿名用户信息放到 anonymous 变量中
            Anonymous anonymous = CQ.getAnonymous(fromAnonymous);
        }
        // 解析CQ码案例 如：[CQ:at,qq=100000]
        // CC.analysis();// 此方法将CQ码解析为可直接读取的对象
        long qqId = CC.getAt(msg);// 此方法为简便方法，获取第一个CQ:At里的QQ号，错误时为：-1000
        // 这里处理消息
        //CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "你发送了这样的消息：" + msg + "\n来自Java插件");
        
        //编译器部分，只处理长度>3的消息
  		if (msg.length() > 3) {
  			try {
  				String output = processMsg(subType, msgId, fromQQ, msg, font);
  				if (output.length() >= 1) { //如果有打印值
  					CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "\n" + output);
  				}
  			} catch (Exception e) {
  				e.printStackTrace();
  			}
  		}
  		
  		//自动谱曲部分 "#m;"标识符是否存在
		if (CQCode.decode(msg).indexOf("#m;") != -1) {
			//是否有内容
			if (CQCode.decode(msg).length() > 3) {
				try {
					//写入rhythm<random_tag>.txt文件
					String tag = randomAlphaNumeric(10);
					String musictag = randomAlphaNumeric(10);
					BufferedWriter writer = new BufferedWriter(new FileWriter("rhythm" + tag + ".txt"));
					writer.write(CQCode.decode(msg));
					writer.close();
					//执行midi生成并转换
					//in Python: 
					//sys.argv[0] = "music.py"
					//sys.argv[1] = tag
					//sys.argv[2] = musictag
					execCmd("Python music.py " + tag + " " + musictag);
					//发送wmv文件
					CQ.sendGroupMsg(fromGroup, "[CQ:record,file=" + musictag + ".wav]");
					//删除临时文件
					File file1 = new File("rhythm" + tag + ".txt");
					File file2 = new File("data/record/" + musictag + ".wav");
					file1.delete();
					file2.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//帮助部分 - 总览
		if (CQCode.decode(msg).indexOf("#help;") != -1) {
			String output = "您好，这里是御坂10032号。目前御坂网络已经实装了以下功能：\n\n"
					+ "1. Python编译器(#p;)\n"
					+ "2. Java编译器(#j;)\n"
					+ "3. CommonLisp编译器(#l;)\n"
					+ "4. 自动音乐生成器(#m;)(请输入#mhelp;来获取音乐生成指南)";
			CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "\n" + output);
		}
		
		//帮助部分 - 音乐生成器
		if (CQCode.decode(msg).indexOf("#mhelp;") != -1) {
			String output = ""
					+ "您好，这里是御坂10032号。这里是音乐生成器的使用指南：\n"
					+ "\n"
					+ "请按照以下范例来生成任意音乐\n"
					+ "#m; ←第一行必须是这个\n"
					+ "0,60,1 ←[从第几拍开始,第几个键(钢琴),持续几拍] 下同\n"
					+ "1,62,1 ←请注意逗号为英文逗号\n"
					+ "2,64,1\n"
					+ "\n"
					+ "参考: 60为中音Do,61为升Do,62为Re,以此类推\n"
					+ "\n"
					+ "备注：过于频繁地发送音乐信息会导致生成失败";
			CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "\n" + output);
		}
		
        return MSG_IGNORE;
	}

	/**
	 * 操作并编译代码
	 * 
	 * @param msg
	 *           原消息
	 * @return 经过处理、准备发送的消息
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public String processMsg(int subType, int msgId, long fromQQ, String msg, int font) throws IOException, InterruptedException {
		String output = new String();
		String indicator = msg.substring(0, 3);
		msg = CQCode.decode(msg);
		if (indicator.equals("#p;")) {
			//Python
			//写入.py文件
			BufferedWriter writer = new BufferedWriter(new FileWriter("pythoncode.py"));
			msg = msg.substring(4);
			writer.write(msg);
			writer.close();
			//执行.py文件并获取输出
			output = execCmd("Python pythoncode.py");
			//删除.py文件
			File file = new File("pythoncode.py");
			file.delete();
		}
		else if (indicator.equals("#j;")) {
			//Java
			msg = msg.substring(4);
			char[] content = msg.toCharArray();
			//查找class名称
			String filename = new String();
			int nameIndex = msg.indexOf("class") + 6;
			while (content[nameIndex] != ' ') {
				filename += content[nameIndex];
				nameIndex+=1;
			}
			//写入.java文件
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".java"));
			//在msg中插入复写Print的部分(前缀)	
			String newcontent = "import java.io.BufferedWriter;import java.io.ByteArrayOutputStream;import java.io.FileWriter;import java.io.PrintStream;";
			int j = msg.indexOf("String[] args") + 14;
			boolean inMain = false;
			for (int x=0;x<content.length;x++) {
				if (x == j) {
					inMain = true;
					if (content[j] != '{') {
						j += 1;
						newcontent += String.valueOf(content[x]);
						continue;
					}
					newcontent += String.valueOf(content[x]);
					String prefix = "ByteArrayOutputStream consoleStorage = new ByteArrayOutputStream();PrintStream newConsole = System.out;System.setOut(new PrintStream(consoleStorage));";
					newcontent += prefix;
				}
				else if (content[x] == '}' && inMain) {
					String post = "String str = consoleStorage.toString();try {BufferedWriter writer = new BufferedWriter(new FileWriter(\"javaoutput.txt\"));writer.write(str);writer.close();} catch (Exception e) {}";
					newcontent += post;
					newcontent += String.valueOf(content[x]);
					inMain = false;
				}
				else {
					newcontent += String.valueOf(content[x]);
				}
			}
			writer.write(newcontent);
			writer.close();
			//编译&执行.java文件，生成txt输出文件，读取txt文件
			Process p = Runtime.getRuntime().exec("cmd /c javac " + filename + ".java && java " + filename + " && type javaoutput.txt");
			BufferedReader outputreader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s; 
	        while ((s = outputreader.readLine()) != null){
	            output+=s;
	        }
			//删除临时文件
			File file1 = new File(filename + ".java");
			file1.delete();
			File file2 = new File("javaoutput.txt");
			file2.delete();
			File file3 = new File(filename + ".class");
			file3.delete();
		}
		else if (indicator.equals("#l;")) {
			//Common-Lisp
			//写入.lisp文件
			BufferedWriter writer = new BufferedWriter(new FileWriter("lispcode.lisp"));
			msg = msg.substring(4);
			writer.write(msg);
			writer.close();
			//执行.lisp文件并获取输出
			output = execCmd("sbcl --script lispcode.lisp");
			//删除.lisp文件
			File file = new File("lispcode.lisp");
			file.delete();
		}
		return output;
	}
	


	/**
	 * Generate random string based on given length
	 * @param count
	 * @return
	 */	
	public static String randomAlphaNumeric(int count) {
		String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	/**
	 * 执行系统命令
	 * 
	 * @param cmd
	 * @return
	 * @throws java.io.IOException
	 */
	public static String execCmd(String cmd) throws java.io.IOException {
	    @SuppressWarnings("resource")
		java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	/**
	 * 讨论组消息 (Type=4)<br>
	 * 本方法会在酷Q【线程】中被调用。<br>
	 *
	 * @param subtype
	 *            子类型，目前固定为1
	 * @param msgId
	 *            消息ID
	 * @param fromDiscuss
	 *            来源讨论组
	 * @param fromQQ
	 *            来源QQ号
	 * @param msg
	 *            消息内容
	 * @param font
	 *            字体
	 * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
	 */
	public int discussMsg(int subtype, int msgId, long fromDiscuss, long fromQQ, String msg, int font) {
		// 这里处理消息
		
		return MSG_IGNORE;
	}

	/**
	 * 群文件上传事件 (Type=11)<br>
	 * 本方法会在酷Q【线程】中被调用。<br>
	 *
	 * @param subType
	 *            子类型，目前固定为1
	 * @param sendTime
	 *            发送时间(时间戳)// 10位时间戳
	 * @param fromGroup
	 *            来源群号
	 * @param fromQQ
	 *            来源QQ号
	 * @param file
	 *            上传文件信息
	 * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
	 */
	public int groupUpload(int subType, int sendTime, long fromGroup, long fromQQ, String file) {
		GroupFile groupFile = CQ.getGroupFile(file);
		if(groupFile == null){ // 解析群文件信息，如果失败直接忽略该消息
			return MSG_IGNORE;
		}
		// 这里处理消息
		
		return MSG_IGNORE;
	}

	/**
	 * 群事件-管理员变动 (Type=101)<br>
	 * 本方法会在酷Q【线程】中被调用。<br>
	 *
	 * @param subtype
	 *            子类型，1/被取消管理员 2/被设置管理员
	 * @param sendTime
	 *            发送时间(时间戳)
	 * @param fromGroup
	 *            来源群号
	 * @param beingOperateQQ
	 *            被操作QQ
	 * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
	 */
	public int groupAdmin(int subtype, int sendTime, long fromGroup, long beingOperateQQ) {
		// 这里处理消息
		
		return MSG_IGNORE;
	}

	/**
	 * 群事件-群成员减少 (Type=102)<br>
	 * 本方法会在酷Q【线程】中被调用。<br>
	 *
	 * @param subtype
	 *            子类型，1/群员离开 2/群员被踢
	 * @param sendTime
	 *            发送时间(时间戳)
	 * @param fromGroup
	 *            来源群号
	 * @param fromQQ
	 *            操作者QQ(仅子类型为2时存在)
	 * @param beingOperateQQ
	 *            被操作QQ
	 * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
	 */
	public int groupMemberDecrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ) {
		// 这里处理消息
		
		return MSG_IGNORE;
	}

	/**
	 * 群事件-群成员增加 (Type=103)<br>
	 * 本方法会在酷Q【线程】中被调用。<br>
	 * 
	 * @param subtype
	 *            子类型，1/管理员已同意 2/管理员邀请
	 * @param sendTime
	 *            发送时间(时间戳)
	 * @param fromGroup
	 *            来源群号
	 * @param fromQQ
	 *            操作者QQ(即管理员QQ)
	 * @param beingOperateQQ
	 *            被操作QQ(即加群的QQ)
	 * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
	 */
	public int groupMemberIncrease(int subtype, int sendTime, long fromGroup, long fromQQ, long beingOperateQQ) {
		// 这里处理消息
		
		return MSG_IGNORE;
	}

	/**
	 * 好友事件-好友已添加 (Type=201)<br>
	 * 本方法会在酷Q【线程】中被调用。<br>
	 *
	 * @param subtype
	 *            子类型，目前固定为1
	 * @param sendTime
	 *            发送时间(时间戳)
	 * @param fromQQ
	 *            来源QQ
	 * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
	 */
	public int friendAdd(int subtype, int sendTime, long fromQQ) {
		// 这里处理消息
		
		return MSG_IGNORE;
	}

	/**
	 * 请求-好友添加 (Type=301)<br>
	 * 本方法会在酷Q【线程】中被调用。<br>
	 *
	 * @param subtype
	 *            子类型，目前固定为1
	 * @param sendTime
	 *            发送时间(时间戳)
	 * @param fromQQ
	 *            来源QQ
	 * @param msg
	 *            附言
	 * @param responseFlag
	 *            反馈标识(处理请求用)
	 * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
	 */
	public int requestAddFriend(int subtype, int sendTime, long fromQQ, String msg, String responseFlag) {
		// 这里处理消息
		
		/**
		 * REQUEST_ADOPT 通过
		 * REQUEST_REFUSE 拒绝
		 */
		
		// CQ.setFriendAddRequest(responseFlag, REQUEST_ADOPT, null); // 同意好友添加请求
		return MSG_IGNORE;
	}

	/**
	 * 请求-群添加 (Type=302)<br>
	 * 本方法会在酷Q【线程】中被调用。<br>
	 *
	 * @param subtype
	 *            子类型，1/他人申请入群 2/自己(即登录号)受邀入群
	 * @param sendTime
	 *            发送时间(时间戳)
	 * @param fromGroup
	 *            来源群号
	 * @param fromQQ
	 *            来源QQ
	 * @param msg
	 *            附言
	 * @param responseFlag
	 *            反馈标识(处理请求用)
	 * @return 关于返回值说明, 见 {@link #privateMsg 私聊消息} 的方法
	 */
	public int requestAddGroup(int subtype, int sendTime, long fromGroup, long fromQQ, String msg,
			String responseFlag) {
		// 这里处理消息
		
		/**
		 * REQUEST_ADOPT 通过
		 * REQUEST_REFUSE 拒绝
		 * REQUEST_GROUP_ADD 群添加
		 * REQUEST_GROUP_INVITE 群邀请
		 */
		
		/*if(subtype == 1){ // 本号为群管理，判断是否为他人申请入群 
			CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_ADD, REQUEST_ADOPT, null);// 同意入群
		}
		if(subtype == 2){
			CQ.setGroupAddRequest(responseFlag, REQUEST_GROUP_INVITE, REQUEST_ADOPT, null);// 同意进受邀群
		}*/
		
		return MSG_IGNORE;
	}

}