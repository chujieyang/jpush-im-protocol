package com.jpush.protocal.im.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.jpush.im.protobuf.Im.Protocol;

public class ImLogoutResponse extends BaseResponse {
	
	private Protocol protocol;
	public ImLogoutResponse(int version, long rid, long juid, Protocol protocol) {
		super(version, rid, juid);
		this.protocol = protocol;
	}
	
	@Override
	public void buidResponseBody() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try{
			bos.write(protocol.toByteArray());
			this.mBody = bos.toByteArray();
		} catch (Exception e) {
			try {
				bos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
