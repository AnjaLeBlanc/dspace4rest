package org.dspace.rest.common;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bitstreams")
public class Bitstreams {
	List<BitstreamEntity> bitstreams;

	public List<BitstreamEntity> getBitstreams() {
		return bitstreams;
	}

	public void setBitstreams(List<BitstreamEntity> bitstreams) {
		this.bitstreams = bitstreams;
	}
	
	public void addBitstream(BitstreamEntity entity) {
		if(bitstreams==null){
			bitstreams=new ArrayList<BitstreamEntity>();
		}
		bitstreams.add(entity);
	}
	
	

}
