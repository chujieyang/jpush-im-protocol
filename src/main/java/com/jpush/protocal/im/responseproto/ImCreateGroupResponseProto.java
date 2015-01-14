package com.jpush.protocal.im.responseproto;

import com.jpush.im.protobuf.Group;
import com.jpush.im.protobuf.Group.CreateGroup;
import com.jpush.im.protobuf.Im;
import com.jpush.im.protobuf.Im.Protocol;
import com.jpush.im.protobuf.Im.ProtocolBody;
import com.jpush.im.protobuf.Message;
import com.jpush.im.protobuf.Message.GroupMsg;
import com.jpush.im.protobuf.Message.SingleMsg;

public class ImCreateGroupResponseProto extends BaseProtobufResponse {
	private long gid;
	public ImCreateGroupResponseProto(Protocol protocol) {
		super(protocol);
	}

	@Override
	protected void buildResposneBody() {
		Im.Response.Builder responseBuilder = Im.Response.newBuilder();
		responseBuilder.setCode(this.getCode());
		responseBuilder.setMessage(this.getMessage());
		
		Im.ProtocolBody body = this.protocol.getBody();
		
		Group.CreateGroup createGroupBean = this.protocol.getBody().getCreateGroup();
		createGroupBean = CreateGroup.newBuilder(createGroupBean).setGid(this.gid).build();
		body = ProtocolBody.newBuilder(body).setCreateGroup(createGroupBean).build();
		body = ProtocolBody.newBuilder(body).setCommonRep(responseBuilder).build();
		protocol = Protocol.newBuilder(protocol).setBody(body).build();
	}

	public ImCreateGroupResponseProto setGid(long gid) {
		this.gid = gid;
		return this;
	}
	
}
