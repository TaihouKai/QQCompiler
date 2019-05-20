# QQCompiler

-------------------

> QQ群内自动检测并执行代码的机器人 —— 支持多种语言

> 作者添加了一些额外功能，下有详细目录

> [旧版传送门](https://github.com/TaihouKai/QQCompiler-Old-Version)

-------------------

### 本项目使用了以下工具

* [酷Q Pro](https://cqp.cc/forum.php) by CoolQ (付费软件，未包含在本项目内)

* [MIDITime](https://github.com/cirlabs/miditime) by cirlabs (使用pip install安装，未包含在本项目内）

* [JCQ](https://github.com/Sobte/JCQ-CoolQ) by Sobte （包含在本项目内，请勿自行更新JCQ版本）

* [TiMidity++](https://www.timidity.jp/) by Masanao Izumo (包含在本项目内，并做了部分修改）

-------------------

<img src="https://github.com/TaihouKai/QQCompiler-Old-Version/blob/master/image/attention.png?raw=true" width="20%">

#### 由于本项目未对代码做过滤，有严重的安全威胁，请谨慎使用 ####

#### 切勿在不信任的群里开放，造成财产损失后果自负 ####

#### 请极力避免使用私人计算机启用本机器人，后果自负 ####

-------------------

### 运行指南

* 购买并下载酷Q-Pro（酷Q-Air不支持此插件的音频输出功能，但是可以正常运行其他功能）。

* 下载[CoolQ-DIR](https://github.com/TaihouKai/QQCompiler-CoolQ-DIR)目录下所有内容，并移动/覆盖/合并这些内容到酷Q根目录

* <del>找到freepats.zip文件，解压</del> 暂不需要此步骤。此项改动<strong>可能</strong>会造成一些问题。目前还不确定。

* <del>找到timiditydrv.zip文件，解压</del>

* 下载[32位JRE](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)（64位JRE不支持酷Q），放到app\/com.sobte.cqp.jcq\/conf\/目录下

* 打开app\/com.sobte.cqp.jcq\/conf\/目录下的settings.ini文件，修改JrePath指向刚刚下载的JRE文件夹

* 准备好所有被支持语言的运行环境以及系统环境变量

* 执行pip install miditime，安装miditime

* 运行酷Q，登录QQ机器人账号，右键状态栏图标进入应用-应用管理，启用JavaCQ SDK Tool

-------------------


### 开发指南

* 将酷Q成功准备成可以运行本机器人的状态(参考运行指南)

* 将本项目导入到Eclipse里，修改源码

* 可能需要添加lib里的3个jar到库。右键Project，选择Properties -> Java Build Path。

* 输出jar：右键项目点击Export，选择JAVA -> JAR File，点击Next

* 设置成以下的样子

<img src="https://github.com/TaihouKai/QQCompiler/blob/master/img/export.png?raw=true" width="50%">

* 点击Finish

* 将生成的jar文件放到酷Q目录下的\app\com.sobte.cqp.jcq\app，覆盖。

-------------------

### 支持语言目录

* 任何代码的开头都必须是“<触发符号>”

| 语言 | 触发符号 |
| --- | --- |
| Python | #p; |
| Java | #j; |
| CommonLisp | #l; |

-------------------

### 使用范例

<img src="https://github.com/TaihouKai/QQCompiler-Old-Version/blob/master/image/sample_screenshot.jpg?raw=true" width="100%">

-------------------

### 额外功能目录

* 额外功能的帮助请开启机器人后查看使用帮助

* 或者查看[源代码](https://github.com/TaihouKai/QQCompiler/blob/master/src/com/mainpackage/CoolQ.java)中使用帮助字符串的部分

| 类别 | 名称 | 描述 | 触发符号 |
| --- | --- | --- | --- |
| 帮助 | 使用指南 | 详细的使用指南以及功能目录 | #help; |
| 帮助 | 音乐生成指南 | 音乐生成器的使用方法以及范例 | #mhelp; |
| 帮助 | 跑团数据记录器使用指南 | 跑团数据库的使用方法 | #trpghelp; |
| 工具 | 音乐生成器 | 根据输入的音符信息输出音频 | #m; |
| 工具 | 跑团数据记录器 | 记录跑团数据 | ; |
| 工具 | 变更跑团游戏管理员 | 只有管理员可以访问跑团数据库 | ;KP |
| 工具 | 骰子 | 跑团用的骰子 | #r |
| 工具 | 悄悄话 | 对特定人说匿名悄悄话 | #to.目标QQ:msg |

-------------------

### 使用指南：Python篇

例

```python
#p;
print("Hello, Python!")
```

-------------------

### 使用指南：Java篇

* 不要在大括号前后输入大于一个空格

* 除了System.out.print/ln外尽量不要调用System.out

例

```java
#j;
public class myClass { 
public static void main(String[] args) {
System.out.print("Hello, world!");
}
}
```

-------------------

### 使用指南：CommonLisp篇

例

```cl
#l;
(format t "Hello, CommonLisp!")
```
