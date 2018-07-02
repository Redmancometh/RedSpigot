package net.minecraft.server;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class PathfinderGoal {
	@Autowired
	protected GenericAttributes attr;
	private int a;

	public PathfinderGoal() {
	}

	public abstract boolean a();

	public boolean b() {
		return this.a();
	}

	public boolean g() {
		return true;
	}

	public void c() {
	}

	public void d() {
	}

	public void e() {
	}

	public void a(int i) {
		this.a = i;
	}

	public int h() {
		return this.a;
	}
}
