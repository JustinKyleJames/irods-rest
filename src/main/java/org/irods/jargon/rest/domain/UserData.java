/**
 * 
 */
package org.irods.jargon.rest.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.irods.jargon.core.protovalues.UserTypeEnum;
import org.irods.jargon.core.pub.domain.User;
import org.irods.jargon.rest.configuration.RestConfiguration;
import org.irods.jargon.rest.utils.ConfigurationUtils;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

/**
 * Value object wrapping a jargon <code>User</code> object for representation as
 * XML or JSON
 * 
 * @author Mike Conway - DICE (www.irods.org)
 */
@XmlRootElement(name = "user")
@BadgerFish
public class UserData {

	private String name = "";
	private String id = "";
	private String zone = "";
	private String info = "";
	private String comment = "";
	private Date createTime = null;
	private Date modifyTime = null;
	private UserTypeEnum userType = UserTypeEnum.RODS_UNKNOWN;
	private String userDN = "";

	/**
	 * Optional URL for web access
	 */
	private String webAccessURL = "";

	/**
	 * iRODS env settings for this user/grid
	 */
	private String irodsEnv = "";

	/**
	 * 
	 */
	public UserData() {
	}

	/**
	 * Constructor takes the iRODS domain object <code>User</code> and marshalls
	 * it into a <code>UserData</code> object
	 * 
	 * @param user
	 *            {@link User}
	 */
	public UserData(final User user, final RestConfiguration restConfiguration) {
		if (user == null) {
			throw new IllegalArgumentException("null user");
		}

		if (restConfiguration == null) {
			throw new IllegalArgumentException("null restConfiguration");
		}

		comment = user.getComment();
		createTime = user.getCreateTime();
		id = user.getId();
		info = user.getInfo();
		modifyTime = user.getModifyTime();
		name = user.getName();
		userDN = user.getUserDN();
		userType = user.getUserType();
		zone = user.getZone();
		irodsEnv = ConfigurationUtils.buildIrodsEnvForConfigAndUser(
				restConfiguration, user.getName());
		webAccessURL = restConfiguration.getWebInterfaceURL();

	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("UserData:\n");
		stringBuilder.append("  id:");
		stringBuilder.append(id);
		stringBuilder.append("  name:");
		stringBuilder.append(name);
		stringBuilder.append('\n');
		stringBuilder.append("  userType:");
		stringBuilder.append(userType.getTextValue());
		stringBuilder.append('\n');
		stringBuilder.append("  userDn:");
		stringBuilder.append(userDN);
		stringBuilder.append('\n');
		stringBuilder.append("  zone:");
		stringBuilder.append(zone);
		stringBuilder.append('\n');
		stringBuilder.append("  info:");
		stringBuilder.append(info);
		stringBuilder.append('\n');
		stringBuilder.append("  comment:");
		stringBuilder.append(comment);
		return stringBuilder.toString();
	}

	/**
	 * @return the name
	 */
	@XmlAttribute
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	@XmlAttribute
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the zone
	 */
	@XmlElement
	public String getZone() {
		return zone;
	}

	/**
	 * @param zone
	 *            the zone to set
	 */
	public void setZone(final String zone) {
		this.zone = zone;
	}

	/**
	 * @return the info
	 */
	@XmlElement
	public String getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(final String info) {
		this.info = info;
	}

	/**
	 * @return the comment
	 */
	@XmlElement
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(final String comment) {
		this.comment = comment;
	}

	/**
	 * @return the createTime
	 */
	@XmlElement
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the modifyTime
	 */
	@XmlElement
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(final Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the userType
	 */
	@XmlElement
	public UserTypeEnum getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(final UserTypeEnum userType) {
		this.userType = userType;
	}

	/**
	 * @return the userDN
	 */
	@XmlElement
	public String getUserDN() {
		return userDN;
	}

	/**
	 * @param userDN
	 *            the userDN to set
	 */
	public void setUserDN(final String userDN) {
		this.userDN = userDN;
	}

	@XmlAttribute
	public String getIrodsEnv() {
		return irodsEnv;
	}

	public void setIrodsEnv(final String irodsEnv) {
		this.irodsEnv = irodsEnv;
	}

	@XmlAttribute
	public String getWebAccessURL() {
		return webAccessURL;
	}

	public void setWebAccessURL(final String webAccessURL) {
		this.webAccessURL = webAccessURL;
	}

}
