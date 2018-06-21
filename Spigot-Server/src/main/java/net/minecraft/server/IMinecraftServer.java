package net.minecraft.server;

public interface IMinecraftServer {

	void a();

	String b();

	String d_();

	int e_();

	String f_();

	String getVersion();

	int H();

	int I();

	String[] getPlayers();

	String S();

	String getPlugins();

	String executeRemoteCommand(String s);

	boolean isDebugging();

	void info(String s);

	void warning(String s);

	void g(String s);

	void h(String s);
}
