/**
 * 
 */
package org.irods.jargon.rest.commands.user;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.UserGroupAO;
import org.irods.jargon.core.pub.domain.UserGroup;
import org.irods.jargon.rest.auth.RestAuthUtils;
import org.irods.jargon.rest.commands.GenericCommandResponse;
import org.irods.jargon.rest.configuration.RestConfiguration;
import org.irods.jargon.rest.exception.InvalidRequestDataException;
import org.irods.jargon.rest.exception.IrodsRestException;
import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.XmlNsMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Restful services for iRODS user groups
 * 
 * @author Mike Conway - DICE (www.irods.org)
 * 
 */
@Named
@Path("/user_group")
public class UserGroupService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	IRODSAccessObjectFactory irodsAccessObjectFactory;

	@Inject
	RestConfiguration restConfiguration;

	/**
	 * @return the irodsAccessObjectFactory
	 */
	public IRODSAccessObjectFactory getIrodsAccessObjectFactory() {
		return irodsAccessObjectFactory;
	}

	/**
	 * @param irodsAccessObjectFactory
	 *            the irodsAccessObjectFactory to set
	 */
	public void setIrodsAccessObjectFactory(
			final IRODSAccessObjectFactory irodsAccessObjectFactory) {
		this.irodsAccessObjectFactory = irodsAccessObjectFactory;
	}

	/**
	 * Add a user to a given user group.
	 * 
	 * @param authorization
	 *            BasicAuth info
	 * @param UserGroupMembershipRequest
	 *            {@link UserGroupMembershipRequest}
	 * @return {@link GenericCommandResponse}
	 * 
	 */
	@PUT
	@Path("/user")
	@Consumes("application/json")
	@Mapped(namespaceMap = { @XmlNsMap(namespace = "http://irods.org/irods-rest", jsonName = "irods-rest") })
	public GenericCommandResponse addUserToGroup(
			@HeaderParam("Authorization") final String authorization,
			final UserGroupMembershipRequest userAddToGroupRequest)
			throws InvalidRequestDataException, IrodsRestException {
		log.info("addUserToGroup()");
		GenericCommandResponse response = new GenericCommandResponse();

		if (userAddToGroupRequest == null) {
			log.error("request not found");
			throw new InvalidRequestDataException(
					"missing userAddToGroupRequest");
		}

		log.info("userAddToGroupRequest:{}", userAddToGroupRequest);

		if (userAddToGroupRequest.getUserName() == null
				|| userAddToGroupRequest.getUserName().isEmpty()) {
			log.error("user name missing");
			throw new InvalidRequestDataException("missing userName");
		}

		if (userAddToGroupRequest.getUserGroup() == null
				|| userAddToGroupRequest.getUserGroup().isEmpty()) {
			throw new InvalidRequestDataException("missing userGroup");
		}

		if (userAddToGroupRequest.getZone() == null
				|| userAddToGroupRequest.getZone().isEmpty()) {
			throw new InvalidRequestDataException("missing zone");
		}

		try {
			IRODSAccount irodsAccount = RestAuthUtils
					.getIRODSAccountFromBasicAuthValues(authorization,
							restConfiguration);

			UserGroupAO userGroupAO = irodsAccessObjectFactory
					.getUserGroupAO(irodsAccount);
			userGroupAO.addUserToGroup(userAddToGroupRequest.getUserGroup(),
					userAddToGroupRequest.getUserName(),
					userAddToGroupRequest.getZone());

			return response;

		} catch (JargonException je) {
			log.error("Jargon exception in user add", je);
			throw new IrodsRestException(je);
		} finally {
			irodsAccessObjectFactory.closeSessionAndEatExceptions();
		}
	}

	/**
	 * Delete a user from a given user group.
	 * 
	 * @param authorization
	 *            BasicAuth info
	 * @param UserGroupMembershipRequest
	 *            {@link UserGroupMembershipRequest}
	 * @return {@link GenericCommandResponse}
	 * 
	 */
	@DELETE
	@Path("/{userGroup}")
	@Consumes("application/json")
	@Mapped(namespaceMap = { @XmlNsMap(namespace = "http://irods.org/irods-rest", jsonName = "irods-rest") })
	public GenericCommandResponse deleteUserGroup(
			@HeaderParam("Authorization") final String authorization,
			@PathParam("userGroup") final String userGroup)
			throws InvalidRequestDataException, IrodsRestException {
		log.info("deleteUserGroup()");
		GenericCommandResponse response = new GenericCommandResponse();

		if (userGroup == null || userGroup.isEmpty()) {
			throw new InvalidRequestDataException("missing userGroup");
		}

		try {
			IRODSAccount irodsAccount = RestAuthUtils
					.getIRODSAccountFromBasicAuthValues(authorization,
							restConfiguration);

			UserGroupAO userGroupAO = irodsAccessObjectFactory
					.getUserGroupAO(irodsAccount);

			userGroupAO.removeUserGroup(userGroup);
			return response;

		} catch (JargonException je) {
			log.error("Jargon exception", je);
			throw new IrodsRestException(je);
		} finally {
			irodsAccessObjectFactory.closeSessionAndEatExceptions();
		}
	}

	/**
	 * Delete a user from a given user group.
	 * 
	 * @param authorization
	 *            BasicAuth info
	 * @param UserGroupMembershipRequest
	 *            {@link UserGroupMembershipRequest}
	 * @return {@link GenericCommandResponse}
	 * 
	 */
	@DELETE
	@Path("/{userGroup}/user/{userName}")
	@Consumes("application/json")
	@Mapped(namespaceMap = { @XmlNsMap(namespace = "http://irods.org/irods-rest", jsonName = "irods-rest") })
	public GenericCommandResponse deleteUserFromGroup(
			@HeaderParam("Authorization") final String authorization,
			@PathParam("userGroup") final String userGroup,
			@PathParam("userName") final String userName)
			throws InvalidRequestDataException, IrodsRestException {
		log.info("deleteUserFromGroup()");
		GenericCommandResponse response = new GenericCommandResponse();

		if (userGroup == null || userGroup.isEmpty()) {
			throw new InvalidRequestDataException("missing userGroup");
		}

		if (userName == null || userName.isEmpty()) {
			throw new InvalidRequestDataException("missing userName");
		}

		try {
			IRODSAccount irodsAccount = RestAuthUtils
					.getIRODSAccountFromBasicAuthValues(authorization,
							restConfiguration);

			UserGroupAO userGroupAO = irodsAccessObjectFactory
					.getUserGroupAO(irodsAccount);

			userGroupAO.removeUserFromGroup(userGroup, userName,
					irodsAccount.getZone());

			return response;

		} catch (JargonException je) {
			log.error("Jargon exception", je);
			throw new IrodsRestException(je);
		} finally {
			irodsAccessObjectFactory.closeSessionAndEatExceptions();
		}
	}

	/**
	 * Add a new user group
	 * 
	 * @param authorization
	 * @param userGroupAddRequest
	 * @return
	 * @throws InvalidRequestDataException
	 * @throws IrodsRestException
	 */
	@PUT
	@Consumes("application/json")
	@Mapped(namespaceMap = { @XmlNsMap(namespace = "http://irods.org/irods-rest", jsonName = "irods-rest") })
	public GenericCommandResponse addUserGroup(
			@HeaderParam("Authorization") final String authorization,
			final UserGroupRequest userGroupAddRequest)
			throws InvalidRequestDataException, IrodsRestException {
		log.info("addUserGroup()");
		GenericCommandResponse response = new GenericCommandResponse();

		if (userGroupAddRequest == null) {
			log.error("request not found");
			throw new InvalidRequestDataException("missing userGroupAddRequest");
		}

		log.info("userGroupAddRequest:{}", userGroupAddRequest);

		if (userGroupAddRequest.getUserGroupName() == null
				|| userGroupAddRequest.getUserGroupName().isEmpty()) {
			log.error("user groupName missing");
			throw new InvalidRequestDataException("missing userName");
		}

		if (userGroupAddRequest.getZone() == null
				|| userGroupAddRequest.getZone().isEmpty()) {
			throw new InvalidRequestDataException("missing zone");
		}

		try {
			IRODSAccount irodsAccount = RestAuthUtils
					.getIRODSAccountFromBasicAuthValues(authorization,
							restConfiguration);

			UserGroupAO userGroupAO = irodsAccessObjectFactory
					.getUserGroupAO(irodsAccount);

			UserGroup userGroup = new UserGroup();
			userGroup.setUserGroupName(userGroupAddRequest.getUserGroupName());
			userGroup.setZone(userGroupAddRequest.getZone());

			userGroupAO.addUserGroup(userGroup);

			return response;

		} catch (JargonException je) {
			log.error("Jargon exception in user add", je);
			throw new IrodsRestException(je);
		} finally {
			irodsAccessObjectFactory.closeSessionAndEatExceptions();
		}
	}

	/**
	 * @return the restConfiguration
	 */
	public RestConfiguration getRestConfiguration() {
		return restConfiguration;
	}

	/**
	 * @param restConfiguration
	 *            the restConfiguration to set
	 */
	public void setRestConfiguration(final RestConfiguration restConfiguration) {
		this.restConfiguration = restConfiguration;
	}

}