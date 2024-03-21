CREATE TABLE `P_USER` (
                          `USER_SEQ`	INT(10) UNSIGNED	NOT NULL	 AUTO_INCREMENT	COMMENT '회원_시퀀스',
                          `FILE_SEQ`	INT(10) UNSIGNED	NULL	COMMENT '파일_시퀀스',
                          `INCOME_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '수입_시퀀스',
                          `USER_ID`	VARCHAR(20)	NOT NULL	COMMENT '회원_ID',
                          `USER_PW`	VARCHAR(100)	NOT NULL	COMMENT '회원_PW',
                          `USER_NICKNAME`	VARCHAR(30)	NULL	COMMENT '회원_닉네임',
                          `USER_CHK`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '첫_로그인_여부',
                          `USER_BIRTH`	DATETIME	NOT NULL	COMMENT '회원_생년월일',
                          `USER_EMAIL`	VARCHAR(30)	NOT NULL	COMMENT '회원_이메일',
                          `USER_DT`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP	COMMENT '가입_일시',
                          `PRIVATE_YN`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '프로필_공개_여부',
                          `USER_GENDER`	VARCHAR(255)	NOT NULL	COMMENT '성별',
                          `USER_INTRO`	VARCHAR(200)	NULL	COMMENT '유저_소개',
                          `DEL_YN`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '탈퇴_여부',
                          `FOLLOW_ALARM`	CHAR(1)	NOT NULL	DEFAULT 'Y'	COMMENT '팔로우_알림_여부',
                          `POST_ALARM`	CHAR(1)	NOT NULL	DEFAULT 'Y'	COMMENT '댓글, 좋아요_알림_여부',
                          `GROUP_ALARM`	CHAR(1)	NOT NULL	DEFAULT 'Y'	COMMENT '그룹_알림_여부',
                          PRIMARY KEY (`USER_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='회원';

CREATE TABLE `P_FEED` (
                          `FEED_SEQ`	INT(10) UNSIGNED	NOT NULL	AUTO_INCREMENT	COMMENT '피드_시퀀스',
                          `USER_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '작성자',
                          `FEED_CATE_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '피드_카테고리_시퀀스 / 피드 타입이 지출일 경우 필요',
                          `FILE_SEQ`	INT(10) UNSIGNED	NULL	COMMENT '파일_시퀀스',
                          `FEED_TYPE`	CHAR(1)	NOT NULL	COMMENT '피드_타입 / 지출:E, 꿀팁:T, 자유:F',
                          `FEED_CONTENT`	VARCHAR(500)	NULL	COMMENT '피드_내용',
                          `FEED_FILES`	VARCHAR(255)	NULL	COMMENT '피드_파일_목록',
                          `FEED_LIKE`	INT	NOT NULL	DEFAULT 0	COMMENT '피드_좋아요_개수',
                          `FEED_SAVE`	INT	NOT NULL	DEFAULT 0	COMMENT '피드_보관_개수',
                          `FEED_DT`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP	COMMENT '피드_생성일자',
                          PRIMARY KEY (`FEED_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='피드';

CREATE TABLE `P_FEED_SAVE` (
                               `FEED_SAVE_SEQ`	INT(10) UNSIGNED	NOT NULL	 AUTO_INCREMENT	COMMENT '피드_보관함_시퀀스',
                               `USER_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '회원_시퀀스',
                               `FEED_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '피드_시퀀스',
                               PRIMARY KEY (`FEED_SAVE_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='피드 보관함';



CREATE TABLE `P_EXPENSE` (
                             `EXP_SEQ`	INT(10) UNSIGNED	NOT NULL	AUTO_INCREMENT	COMMENT '지출_내역_시퀀스',
                             `USER_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '회원_시퀀스',
                             `FEED_CATE_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '피드_카테고리_시퀀스',
                             `EXP_EXPENSE`	INT	NOT NULL	COMMENT '지출_금액',
                             `EXP_DT`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP COMMENT '등록_일시',
                             `DEL_YN`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '삭제_여부',
                             PRIMARY KEY (`EXP_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='지출_내역';


CREATE TABLE `P_CERT` (
                          `CERT_SEQ`	INT(10) UNSIGNED	NOT NULL	AUTO_INCREMENT	COMMENT '인증_시퀀스',
                          `CERT_CD`	VARCHAR(6)	NOT NULL	COMMENT '인증_코드',
                          `CERT_DT`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP	COMMENT '생성_일시',
                          `DEL_YN`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '삭제_여부',
                          PRIMARY KEY (`CERT_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='이메일_인증 (회원가입 이메일 인증 생각해야 됨)';



CREATE TABLE `P_FEED_CATEGORY` (
                                   `FEED_CATE_SEQ`	INT(10) UNSIGNED	NOT NULL	AUTO_INCREMENT	COMMENT '피드_카테고리_시퀀스',
                                   `USER_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '회원_시퀀스',
                                   `FILE_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '파일_시퀀스',
                                   `FEED_CATE_NM`	VARCHAR(30)	NOT NULL	COMMENT '피드_카테고리_명',
                                   `DEL_YN`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '삭제_여부',
                                   PRIMARY KEY (`FEED_CATE_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='피드_카테고리';



CREATE TABLE `그룹` (
                      `GROUP_SEQ`	INT(10) UNSIGNED	NOT NULL	AUTO_INCREMENT	COMMENT '그룹_시퀀스',
                      `FILE_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '파일_시퀀스',
                      `GROUP_FILTER_SEQ`	INT UNSIGNED	NOT NULL	COMMENT '그룹_제한_시퀀스',
                      `GROUP_NM`	VARCHAR(50)	NOT NULL	COMMENT '그룹_명',
                      `GROUP_INTRO`	VARCHAR(200)	NULL	COMMENT '그룹_소개',
                      `GROUP_DT`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP	COMMENT '생성_일자',
                      `PRIVATE_YN`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '승인받아야하는지_여부',
                      `PERSON_LIMIT`	INT	NOT NULL	COMMENT '회원_수_제한',
                      `DEL_YN`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '삭제_여부',
                      PRIMARY KEY (`GROUP_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='그룹';



CREATE TABLE `P_FOLLOW` (
                            `FOLLOW_SEQ`	INT UNSIGNED	NOT NULL	AUTO_INCREMENT	COMMENT '팔로우/팔로워_시퀀스',
                            `FOLLOWING_SEQ`	INT UNSIGNED	NOT NULL	COMMENT '팔로잉',
                            `FOLLOWER_SEQ`	INT UNSIGNED	NOT NULL	COMMENT '팔로워',
                            `FOLLOW_YN`	CHAR(1)	NOT NULL	DEFAULT 'A'	COMMENT '팔로우_진행_상황 / F:팔로우_완료 , A : 팔로우_승인_대기',
                            `DEL_YN`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '팔로우_취소_여부',
                            PRIMARY KEY (`FOLLOW_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='팔로우/팔로워';


CREATE TABLE `P_GROUP_PERSON` (
                                  `GROUP_PERSON_SEQ`	INT(10) UNSIGNED	NOT NULL	AUTO_INCREMENT	COMMENT '그룹_인원_시퀀스',
                                  `USER_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '회원_시퀀스',
                                  `GROUP_SEQ`	INT(10)	NOT NULL	COMMENT '그룹_시퀀스',
                                  `GROUP_MASTER_YN`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '그룹장_여부',
                                  `APPROVAL_YN`	VARCHAR(255)	NOT NULL	DEFAULT 'A'	COMMENT '승인_진행_상황 / F:승인_완료 , A : 승인_대기',
                                  `DEL_YN`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '탈퇴_여부',
                                  PRIMARY KEY (`GROUP_PERSON_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='그룹_인원';


CREATE TABLE `P_FILE` (
                          `FILE_SEQ`	INT(10) UNSIGNED	NOT NULL	AUTO_INCREMENT	COMMENT '파일_시퀀스',
                          `FILE_TYPE`	CHAR(1)	NULL	COMMENT '파일_타입 / F: 피드, E : 이모티콘,  I : 아이콘,  G : 그룹대표사진 , P : 프로필사진',
                          PRIMARY KEY (`FILE_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='파일';


CREATE TABLE `P_CHAT` (
                          `CHAT_SEQ`	INT(10) UNSIGNED	NOT NULL	AUTO_INCREMENT	COMMENT '채팅_시퀀스',
                          `CHAT_TYPE`	CHAR(1)	NOT NULL	COMMENT 'P : 일대일 / G : 그룹',
                          `CHAT_CONTENT`	VARCHAR(500)	NOT NULL	COMMENT '일반 텍스트, 그룹이면 피드 등록 알림 채팅 등',
                          `CHAT_DT`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP	COMMENT '채팅_발송일시',
                          `PARTY_SEQ`	INT UNSIGNED	NOT NULL	COMMENT '상대방 혹은 그룹 시퀀스',
                          `SYSTEM_YN`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '시스템 알림 (특정 회원이 돈을 가장 많이 썼습니다, 누가 방을 나갔습니다 등)',
                          PRIMARY KEY (`CHAT_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='채팅';



CREATE TABLE `P_COMMENT` (
                             `COMMENT_SEQ`	INT(10) UNSIGNED	NOT NULL	AUTO_INCREMENT	COMMENT '댓글_시퀀스',
                             `FEED_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '피드_시퀀스',
                             `USER_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '회원_시퀀스',
                             `COMMENT_CONTENT`	VARCHAR(200)	NOT NULL	COMMENT '댓글_내용',
                             `COMMENT_PARENT_SEQ`	INT(10) UNSIGNED	NULL	COMMENT '부모_댓글_시퀀스',
                             `COMMENT_LIKE`	INT	NOT NULL	DEFAULT 0	COMMENT '댓글_좋아요_개수',
                             `COMMENT_DT`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP	COMMENT '댓글_작성일자',
                             `DEL_YN`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '삭제_여부',
                             PRIMARY KEY (`COMMENT_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='댓글';



CREATE TABLE `P_ALARM` (
                           `ALARM_SEQ`	INT(10) UNSIGNED	NOT NULL	AUTO_INCREMENT	COMMENT '알림_시퀀스',
                           `USER_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '회원_시퀀스',
                           `ALARM_TYPE`	CHAR(1)	NOT NULL	COMMENT '알림_타입',
                           `ALARM_CONTENT`	VARCHAR(200)	NOT NULL	COMMENT '알림_내용',
                           `ALARM_DT`	TIMESTAMP	NOT NULL	DEFAULT CURRENT_TIMESTAMP	COMMENT '알림_발송일시',
                           `ALARM_READ`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '알림_읽음_여부',
                           `DEL_YN`	CHAR(1)	NOT NULL	DEFAULT 'N'	COMMENT '삭제_여부',
                           PRIMARY KEY (`ALARM_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='알림';



CREATE TABLE `P_CHAT_READ` (
                               `CHAT_READ_SEQ`	INT(10) UNSIGNED	NOT NULL	AUTO_INCREMENT	COMMENT '채팅_읽음_시퀀스',
                               `CHAT_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '채팅_시퀀스',
                               `USER_SEQ`	INT(10) UNSIGNED	NOT NULL	COMMENT '회원_시퀀스',
                               PRIMARY KEY (`CHAT_READ_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='채팅_읽은_인원';


CREATE TABLE `P_GROUP_FILTER` (
                                  `GROUP_FILTER_SEQ`	INT(10) UNSIGNED	NOT NULL	AUTO_INCREMENT	COMMENT '그룹_제한_시퀀스',
                                  `GROUP_FILTER_TYPE`	CHAR(1)	NOT NULL	COMMENT '나이, 성별, 수입 제한',
                                  PRIMARY KEY (`GROUP_FILTER_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='그룹_제한';


CREATE TABLE `P_INCOME` (
                            `INCOME_SEQ`	INT(10)_UNSIGNED	NOT NULL	AUTO_INCREMENT	COMMENT '수입_시퀀스',
                            `INCOME`	VARCHAR(20)	NOT NULL	COMMENT '수입_범위',
                            PRIMARY KEY (`INCOME_SEQ`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='수입';