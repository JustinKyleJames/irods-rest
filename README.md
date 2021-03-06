* Project: iRODS Rest API
* Date: 6/29/2015
* Release Version: 4.0.2.3-SNAPSHOT	
* Git tag: 4.0.2.3-RC1

https://github.com/DICE-UNC/irods-rest

iRODS Rest API based on Jargon 4.0.2.3, certified against iRODS 3.0+ as well as iRODS Consortium 4.1.x releases.  See included docs folder for comprehensive user documentation and install instructions

See https://github.com/DICE-UNC/irods-rest/issues for support and known issues


### Requirements

* Depends on Java 1.7+
* Built using Apache Maven2, see POM for dependencies


### Bug Fixes

### Features

#### #2 CORS header support

Add support for Cross Origin Resource Sharing through customizable configuration

#### Add PAM support #6

Add support for PAM authentication through customizable configuration

#### add temp password for user #10

New /user/userName/temppassword signatures available to obtain a temporary iRODS password, including in admin mode.

#### use packing i/o for stream performance #14

Added optional use of packing input and output streams for upload and download.  This uses a simple read-ahead and write-behind buffer approach to optimize iRODS buffer sizes.  The behavior may be controlled by adjusting the configuration property for 'utilizePackingStreams' in RestConfig.
