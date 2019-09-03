CREATE TABLE `term` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `term_id` text NOT NULL,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

CREATE TABLE `subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `termID` int(11) NOT NULL,
  `abbreviation` text NOT NULL,
  `name` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_term_id` (`termID`),
  CONSTRAINT `fk_term_id` FOREIGN KEY (`termID`) REFERENCES `term` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subjectID` int(11) NOT NULL,
  `num` text NOT NULL,
  `name` text NOT NULL,
  `credits` int(11) NOT NULL,
  `prerequisites` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_subject_id` (`subjectID`),
  CONSTRAINT `fk_subject_id` FOREIGN KEY (`subjectID`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `section` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseID` int(11) NOT NULL,
  `crn` int(11) NOT NULL,
  `code` text NOT NULL,
  `instructor` text NOT NULL,
  `capacity` int(11) NOT NULL,
  `remaining` int(11) NOT NULL,
  `waitlistCapacity` int(11) NOT NULL,
  `waitlistRemaining` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_course_id` (`courseID`),
  CONSTRAINT `fk_course_id` FOREIGN KEY (`courseID`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `section_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sectionID` int(11) NOT NULL,
  `days` text NOT NULL,
  `startTime` int(11) NOT NULL,
  `endTime` int(11) NOT NULL,
  `location` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_section_id` (`sectionID`),
  CONSTRAINT `fk_section_id` FOREIGN KEY (`sectionID`) REFERENCES `section` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE DEFINER=`root`@`localhost` PROCEDURE `ADD_TERM`(IN `term_id` TEXT, IN `name` TEXT)
BEGIN
	INSERT INTO `term`(term_id, name) VALUES(term_id, name);
    SELECT LAST_INSERT_ID() as id;
END;

CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_TERMS`()
BEGIN
	SELECT * FROM term;
END;

