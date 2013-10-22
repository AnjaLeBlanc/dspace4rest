/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.rest.common;

import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: peterdietz
 * Date: 9/21/13
 * Time: 12:54 AM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "bitstreams")
public class Bitstream extends DSpaceObject {
    Logger log = Logger.getLogger(Bitstream.class);

    String bundleName;
    String description;
    String format;
    String mimeType;
    Long sizeBytes;
    CheckSum checkSum;
    String retrieveLink;
    

    public Bitstream() {

    }

    public Bitstream(org.dspace.content.Bitstream bitstream, String expand) {
        super(bitstream);
        setup(bitstream, expand);
    }

    public void setup(org.dspace.content.Bitstream bitstream, String expand) {
        try {
            this.setBundleName(bitstream.getBundles()[0].getName());
            this.setDescription(bitstream.getDescription());
            this.setFormat(bitstream.getFormatDescription());
            this.setSizeBytes(bitstream.getSize());
            this.setRetrieveLink("/bitstreams/" + bitstream.getID() + "/retrieve");
            this.setMimeType(bitstream.getFormat().getMIMEType());
            CheckSum checkSum = new CheckSum();
            checkSum.setCheckSumAlgorith(bitstream.getChecksumAlgorithm());
            checkSum.setValue(bitstream.getChecksum());
            this.setCheckSum(checkSum);
            
            log.debug("mimeType " + mimeType + " cs " + checkSum.getValue());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public String getBundleName() {
        return bundleName;
    }

    public String getDescription() {
        return description;
    }

    public String getFormat() {
        return format;
    }

    public String getMimeType() {
    	log.debug("mimeType called" + mimeType);
        return mimeType;
    }

    public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public void setSizeBytes(Long sizeBytes) {
		this.sizeBytes = sizeBytes;
	}

	public void setCheckSum(CheckSum checkSum) {
		this.checkSum = checkSum;
	}

	public void setRetrieveLink(String retrieveLink) {
		this.retrieveLink = retrieveLink;
	}

	public Long getSizeBytes() {
        return sizeBytes;
    }

    public String getRetrieveLink() {
        return retrieveLink;
    }
    
    public CheckSum getCheckSum() {
		return checkSum;
	}

}
