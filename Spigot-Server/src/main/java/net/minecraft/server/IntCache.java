package net.minecraft.server;

import com.google.common.collect.Lists;

import net.mcavenue.redspigot.configuration.pojo.spigot.SpigotConfig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class IntCache {

	private int a = 256;
	private final List<int[]> b = Lists.newArrayList();
	private final List<int[]> c = Lists.newArrayList();
	private final List<int[]> d = Lists.newArrayList();
	private final List<int[]> e = Lists.newArrayList();
	@Autowired
	private SpigotConfig cfg;
	public synchronized int[] a(int i) {
		int[] aint;

		if (i <= 256) {
			if (b.isEmpty()) {
				aint = new int[256];
				if (c.size() < cfg.getIntCacheLimit())
					c.add(aint);
				return aint;
			} else {
				aint = (int[]) b.remove(b.size() - 1);
				if (c.size() < cfg.getIntCacheLimit())
					c.add(aint);
				return aint;
			}
		} else if (i > a) {
			a = i;
			d.clear();
			e.clear();
			aint = new int[a];
			if (e.size() < cfg.getIntCacheLimit())
				e.add(aint);
			return aint;
		} else if (d.isEmpty()) {
			aint = new int[a];
			if (e.size() < cfg.getIntCacheLimit())
				e.add(aint);
			return aint;
		} else {
			aint = (int[]) d.remove(d.size() - 1);
			if (e.size() < cfg.getIntCacheLimit())
				e.add(aint);
			return aint;
		}
	}

	public synchronized void a() {
		if (!d.isEmpty()) {
			d.remove(d.size() - 1);
		}

		if (!b.isEmpty()) {
			b.remove(b.size() - 1);
		}

		d.addAll(e);
		b.addAll(c);
		e.clear();
		c.clear();
	}

	public synchronized String b() {
		return "cache: " + d.size() + ", tcache: " + b.size() + ", allocated: " + e.size() + ", tallocated: " + c.size();
	}
}
