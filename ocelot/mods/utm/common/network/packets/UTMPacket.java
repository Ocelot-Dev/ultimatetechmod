package ocelot.mods.utm.common.network.packets;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public abstract class UTMPacket
{
	public int packetId;
	protected int packetType;
	protected boolean isChunkDataPacket = false;
	protected String channel = "UTM";

	public int getPacketId()
	{
		return this.packetId;
	}

	public int getPacketType() // 1: facing data 2: tile update
	{
		return this.packetType;
	}

	public Packet getPacket()
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		try
		{
			data.writeByte(getPacketType());
			data.writeByte(getPacketId());
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

	public static NBTTagCompound readNBTTagCompound(DataInputStream data) throws IOException
	{
		short length = data.readShort();
		byte[] compressed = new byte[length];
		data.readFully(compressed);
		return CompressedStreamTools.decompress(compressed);
	}

	/**
	 * Writes a compressed NBTTagCompound to the OutputStream
	 */
	protected static void writeNBTTagCompound(NBTTagCompound tag, DataOutputStream data) throws IOException
	{
		byte[] compressed = CompressedStreamTools.compress(tag);
		data.writeShort(compressed.length);
		data.write(compressed);
	}

}
