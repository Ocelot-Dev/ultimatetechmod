package ocelot.mods.utm.common.network.packets;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public abstract class UTMPacket
{
	protected int packetId;
	protected int packetType;
	protected boolean isChunkDataPacket = false;
	protected String channel = "oilC";
	
	public int getPacketId()
	{
		return this.packetId;
	}
	
	public int getPacketType() // 1: facing data
	{
		return this.packetType;
	}
	
	public Packet getPacket()
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		try
		{
			data.writeByte(getPacketId());
			data.writeByte(getPacketType());
			writeData(data);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = this.channel;
		packet.data = bytes.toByteArray();
		packet.length = packet.data.length;
		packet.isChunkDataPacket = this.isChunkDataPacket;
		return packet;
	}
	
	public abstract void readData(DataInputStream paramDataInputStream) throws IOException;
	
	public abstract void writeData(DataOutputStream paramDataOutputStream) throws IOException;

}
