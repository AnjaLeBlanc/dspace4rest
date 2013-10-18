package org.dspace.rest.common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.dspace.core.Context;
import org.apache.log4j.Logger;

@XmlRootElement(name = "bitstream")
public class BitstreamEntity {
	
	CheckSum checkSum;
	
	String description;
	
	Integer bitstreamID;
	
	String mimeType;
	
	String name;
	
	Integer sequenceID;
	
	Long size;
	
	private org.dspace.content.Bitstream bs;
	
	private static Context context;
	
	 /** log4j category */
    private static final Logger log = Logger.getLogger(BitstreamEntity.class);
	
	
	public BitstreamEntity(){}
	
	public BitstreamEntity(Integer bitstreamID){
		try {
            if(context == null || !context.isValid()) {
                context = new Context();
            }

            bs= org.dspace.content.Bitstream.find(context, bitstreamID);
            setup(bs, null);
        } catch(Exception e) {
            //TODO
        }
	}
	
	public BitstreamEntity(Integer bitstreamID, String expand) {
        try {
            if(context == null || !context.isValid()) {
                context = new Context();
            }

            bs= org.dspace.content.Bitstream.find(context, bitstreamID);
            setup(bs, expand);
        } catch(Exception e) {
            //TODO
        }
    }
	
	public CheckSum getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(CheckSum checkSum) {
		this.checkSum = checkSum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getBitstreamID() {
		return bitstreamID;
	}

	public void setBitstreamID(Integer bitstreamID) {
		this.bitstreamID = bitstreamID;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSequenceID() {
		return sequenceID;
	}

	public void setSequenceID(Integer sequenceID) {
		this.sequenceID = sequenceID;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

    private void setup(org.dspace.content.Bitstream bs, String expand) {
        List<String> expandFields = new ArrayList<String>();
        if(expand != null) {
            expandFields = Arrays.asList(expand.split(","));
        }

        this.setBitstreamID(bs.getID());
        CheckSum checkSum = new CheckSum();
        checkSum.setCheckSumAlgorith(bs.getChecksumAlgorithm());
        checkSum.setValue(bs.getChecksum());
        this.setCheckSum(checkSum);
        this.setDescription(bs.getDescription());
        this.setMimeType(bs.getFormat().getMIMEType());
        this.setName(bs.getName());
        this.setSequenceID(bs.getSequenceID());
        this.setSize(bs.getSize());
        
    }
    
    public InputStream getFile(){    	
    	try {
			return bs.retrieve();
		} catch (Exception e) {
			log.error(e);
			return null;
		} 
    }

}
