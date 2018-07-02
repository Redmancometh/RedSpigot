package net.minecraft.server;

// CraftBukkit start
import java.net.InetAddress;
import java.util.HashMap;
// CraftBukkit end

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import net.mcavenue.redspigot.configuration.pojo.spigot.SpigotConfig;

/**
 * TODON: Important fixes here.
 * 
 * @author Redmancometh
 *
 */
public class HandshakeListener implements PacketHandshakingInListener {

	private static final com.google.gson.Gson gson = new com.google.gson.Gson(); // Spigot
	// CraftBukkit start - add fields
	private static final HashMap<InetAddress, Long> throttleTracker = new HashMap<InetAddress, Long>();
	private static int throttleCounter = 0;
	// CraftBukkit end
	@Autowired
	public AutowireCapableBeanFactory beans;
	@Autowired
	private SpigotConfig cfg;
	private final MinecraftServer a;
	private final NetworkManager b;

	public HandshakeListener(MinecraftServer minecraftserver, NetworkManager networkmanager) {
		this.a = minecraftserver;
		this.b = networkmanager;
	}

	public void a(PacketHandshakingInSetProtocol packethandshakinginsetprotocol) {
		switch (packethandshakinginsetprotocol.a()) {
			case LOGIN :
				this.b.setProtocol(EnumProtocol.LOGIN);
				ChatMessage chatmessage;

				// CraftBukkit start - Connection throttle
				try {
					long currentTime = System.currentTimeMillis();
					long connectionThrottle = MinecraftServer.getServer().server.getConnectionThrottle();
					InetAddress address = ((java.net.InetSocketAddress) this.b.getSocketAddress()).getAddress();

					synchronized (throttleTracker) {
						if (throttleTracker.containsKey(address) && !"127.0.0.1".equals(address.getHostAddress())
								&& currentTime - throttleTracker.get(address) < connectionThrottle) {
							throttleTracker.put(address, currentTime);
							chatmessage = new ChatMessage("Connection throttled! Please wait before reconnecting.");
							this.b.sendPacket(new PacketLoginOutDisconnect(chatmessage));
							this.b.close(chatmessage);
							return;
						}

						throttleTracker.put(address, currentTime);
						throttleCounter++;
						if (throttleCounter > 200) {
							throttleCounter = 0;

							// Cleanup stale entries
							java.util.Iterator iter = throttleTracker.entrySet().iterator();
							while (iter.hasNext()) {
								java.util.Map.Entry<InetAddress, Long> entry = (java.util.Map.Entry) iter.next();
								if (entry.getValue() > connectionThrottle) {
									iter.remove();
								}
							}
						}
					}
				} catch (Throwable t) {
					org.apache.logging.log4j.LogManager.getLogger().debug("Failed to check connection throttle", t);
				}
				// CraftBukkit end

				if (packethandshakinginsetprotocol.b() > 340) {
					chatmessage = new ChatMessage(
							java.text.MessageFormat.format(cfg.getMessages().getOutdatedServer().replaceAll("'", "''"), "1.12.2")); // Spigot
					this.b.sendPacket(new PacketLoginOutDisconnect(chatmessage));
					this.b.close(chatmessage);
				} else if (packethandshakinginsetprotocol.b() < 340) {
					chatmessage = new ChatMessage(
							java.text.MessageFormat.format(cfg.getMessages().getOutdatedClient().replaceAll("'", "''"), "1.12.2")); // Spigot
					this.b.sendPacket(new PacketLoginOutDisconnect(chatmessage));
					this.b.close(chatmessage);
				} else {
					this.b.setPacketListener(new LoginListener(this.a, this.b));
					// Spigot Start
					if (cfg.isBungeecord()) {
						String[] split = packethandshakinginsetprotocol.hostname.split("\00");
						if (split.length == 3 || split.length == 4) {
							packethandshakinginsetprotocol.hostname = split[0];
							b.l = new java.net.InetSocketAddress(split[1], ((java.net.InetSocketAddress) b.getSocketAddress()).getPort());
							b.spoofedUUID = com.mojang.util.UUIDTypeAdapter.fromString(split[2]);
						} else {
							chatmessage = new ChatMessage("If you wish to use IP forwarding, please enable it in your BungeeCord config as well!");
							this.b.sendPacket(new PacketLoginOutDisconnect(chatmessage));
							this.b.close(chatmessage);
							return;
						}
						if (split.length == 4) {
							b.spoofedProfile = gson.fromJson(split[3], com.mojang.authlib.properties.Property[].class);
						}
					}
					// Spigot End
					((LoginListener) this.b.i()).hostname = packethandshakinginsetprotocol.hostname + ":" + packethandshakinginsetprotocol.port; // CraftBukkit
																																					// -
																																					// set
																																					// hostname
				}
				break;

			case STATUS :
				this.b.setProtocol(EnumProtocol.STATUS);
				this.b.setPacketListener(beans.getBean(PacketStatusListener.class));
				break;

			default :
				throw new UnsupportedOperationException("Invalid intention " + packethandshakinginsetprotocol.a());
		}

	}

	public void a(IChatBaseComponent ichatbasecomponent) {
	}
}