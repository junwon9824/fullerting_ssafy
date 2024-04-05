-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: j10C102.p.ssafy.io    Database: fullerting
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `article_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `article_title` varchar(30) NOT NULL,
  `article_content` varchar(5000) NOT NULL,
  `article_created_at` datetime NOT NULL,
  `article_love` int NOT NULL,
  `article_type` varchar(20) NOT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,1,'우리집 고양이 츄르를 좋아해','냥','2024-04-03 02:24:00',2,'자유게시판'),(2,3,'?제가 키운 무순을 소개합니다?','제가 매일 물 주고 정성들여 키운 무순을 소개합니다!\n꾸준히 관심을 주니 아주 싱싱하게 잘 자랐어요ㅎㅎ','2024-04-03 02:24:09',1,'작물소개'),(3,3,'싱싱한 무순 키우는 꿀팁❣️','이번에 무순을 키우면서 직접 얻은 꿀팁입니다!\n무순 씨앗은 발아 전까지는 어둡게 유지해주다가\n발아 후에는 햇빛을 많이 쬐어주면 싱싱한 무순이 자라납니다?','2024-04-03 02:33:56',1,'꿀팁공유'),(4,3,'무순을 올린 육회비빔밥?','요리라고 하기 뭐하지만~ 육회 비빔밥에 제가 직접 키운 무순을 올려서 같이 먹었답니다? 뭔가 더 맛있는 기분~~ 여러분도 한번 직접 키운 작물로 요리해보세요!','2024-04-03 02:49:12',1,'텃밭요리'),(14,9,'만나서 반가워요','ㅎㅎ','2024-04-03 14:45:33',1,'자유게시판');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `badge`
--

DROP TABLE IF EXISTS `badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `badge` (
  `badge_id` int NOT NULL AUTO_INCREMENT,
  `badge_name` varchar(20) NOT NULL,
  `badge_img` varchar(255) NOT NULL,
  `badge_trigger` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`badge_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `badge`
--

LOCK TABLES `badge` WRITE;
/*!40000 ALTER TABLE `badge` DISABLE KEYS */;
INSERT INTO `badge` VALUES (1,'상추를 치료해줄 사람 어디 없나','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/badge_lettuce.svg','상추-3'),(2,'대흥단 왕감자','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/badge_potato.svg','감자-4'),(3,'무순129','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/badge_radish-sprouts.svg','무순-3'),(4,'토마토마토','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/badge_tomato.svg','토마토-4'),(5,'브로~콜리','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/badge_broccoli.svg','브로콜리-4'),(6,'콩남울','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/badge_bean-sprout.svg','콩나물-3');
/*!40000 ALTER TABLE `badge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bid_log`
--

DROP TABLE IF EXISTS `bid_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bid_log` (
  `bid_log_id` int NOT NULL AUTO_INCREMENT,
  `deal_id` int DEFAULT NULL,
  `user_id` int NOT NULL,
  `bid_log_time` timestamp NOT NULL,
  `bid_log_price` int NOT NULL,
  PRIMARY KEY (`bid_log_id`),
  KEY `deal_id` (`deal_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `bid_log_ibfk_1` FOREIGN KEY (`deal_id`) REFERENCES `deal` (`deal_id`),
  CONSTRAINT `bid_log_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bid_log`
--

LOCK TABLES `bid_log` WRITE;
/*!40000 ALTER TABLE `bid_log` DISABLE KEYS */;
INSERT INTO `bid_log` VALUES (2,2,3,'2024-04-03 02:16:16',600),(3,2,3,'2024-04-03 02:16:47',700),(6,5,1,'2024-04-03 02:21:14',500),(12,2,8,'2024-04-03 09:27:47',888),(13,8,2,'2024-04-03 09:28:19',1666),(15,2,8,'2024-04-03 09:47:10',999),(16,8,8,'2024-04-03 09:47:29',1777),(24,8,1,'2024-04-03 10:31:50',2000),(32,1,1,'2024-04-03 11:35:59',111),(33,1,1,'2024-04-03 11:36:01',1112),(48,8,13,'2024-04-03 15:28:07',2200),(49,8,1,'2024-04-03 15:29:06',2300),(51,8,1,'2024-04-03 16:20:36',5000),(54,26,2,'2024-04-03 16:51:20',1100),(55,26,9,'2024-04-03 17:40:43',1101),(56,26,9,'2024-04-03 17:40:54',1102),(57,26,9,'2024-04-03 17:41:20',1103),(58,26,9,'2024-04-03 17:44:56',1104),(59,8,8,'2024-04-04 08:53:20',5100);
/*!40000 ALTER TABLE `bid_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat` (
  `chat_id` int NOT NULL AUTO_INCREMENT,
  `chat_room_id` int NOT NULL,
  `user_id` int NOT NULL,
  `chat_message` varchar(5000) NOT NULL,
  `chat_send_at` timestamp NOT NULL,
  PRIMARY KEY (`chat_id`),
  KEY `chat_room_id` (`chat_room_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `chat_ibfk_1` FOREIGN KEY (`chat_room_id`) REFERENCES `chat_room` (`chat_room_id`) ON DELETE CASCADE,
  CONSTRAINT `chat_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
INSERT INTO `chat` VALUES (1,1,1,'안녕하세요?\n','2024-04-03 02:17:08'),(2,1,1,'토마토 700원 어떠세요?\nㅎㅎ','2024-04-03 02:17:17'),(3,2,1,'안녕하세요?','2024-04-03 02:21:38'),(4,2,3,'안녕하세요 무순 거래하신다고 해서 연락드립니다!','2024-04-03 02:21:51'),(7,2,3,'거래 하겠습니다!','2024-04-03 02:44:03'),(8,4,3,'저 레몬 주실 수 있나요?!?!?','2024-04-03 03:02:42'),(9,4,4,'네 가져가세요~\n','2024-04-03 03:02:49'),(10,5,7,'안녕하세요','2024-04-03 09:28:34'),(11,5,2,'안녕하세요','2024-04-03 09:28:59'),(14,5,3,'안녕하세요','2024-04-03 09:43:44'),(15,2,1,'거래','2024-04-03 09:44:01'),(25,10,3,'일반 거래 테스트','2024-04-03 09:47:48'),(26,10,2,'아하','2024-04-03 09:47:51'),(32,2,3,'안녕하세요','2024-04-03 09:57:54'),(42,17,7,'토마토 사실건가요?','2024-04-03 11:05:41'),(46,19,8,'구매희망합니다','2024-04-03 11:47:15'),(54,24,9,'안녕하세요?','2024-04-03 14:24:43'),(56,23,4,'네저용','2024-04-03 15:03:59'),(59,28,13,'안녕하세요 사과 구매하고싶어요','2024-04-03 15:27:15'),(60,29,1,'안뇽하세요','2024-04-03 16:51:34'),(61,29,2,'구매희망합니다','2024-04-03 16:51:45');
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_room`
--

DROP TABLE IF EXISTS `chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_room` (
  `chat_room_id` int NOT NULL AUTO_INCREMENT,
  `ex_article_id` int NOT NULL,
  `user_id` int NOT NULL,
  `chat_room_seller_id` mediumtext,
  PRIMARY KEY (`chat_room_id`),
  KEY `ex_article_id` (`ex_article_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `chat_room_ibfk_1` FOREIGN KEY (`ex_article_id`) REFERENCES `ex_article` (`ex_article_id`) ON DELETE CASCADE,
  CONSTRAINT `chat_room_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_room`
--

LOCK TABLES `chat_room` WRITE;
/*!40000 ALTER TABLE `chat_room` DISABLE KEYS */;
INSERT INTO `chat_room` VALUES (1,4,3,NULL),(2,8,1,NULL),(4,9,3,NULL),(5,12,2,NULL),(8,4,8,NULL),(10,17,3,NULL),(16,12,1,NULL),(17,12,8,NULL),(19,2,8,NULL),(23,6,4,NULL),(24,3,9,NULL),(27,6,5,NULL),(28,3,13,NULL),(29,38,2,NULL),(30,38,9,NULL);
/*!40000 ALTER TABLE `chat_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `article_id` int NOT NULL,
  `comment_content` varchar(255) DEFAULT NULL,
  `comment_created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (3,1,2,'너무 싱싱하네요~?','2024-04-03 02:25:25'),(4,1,2,'혹시 판매도 하시나요?','2024-04-03 02:25:51'),(6,3,1,'너무 귀여워요ㅠㅠ?','2024-04-03 02:31:06'),(31,4,1,'이쁘다','2024-04-03 14:58:19'),(33,4,1,'이뿌네여','2024-04-03 14:58:42'),(35,1,4,'저도 직접 키운 무순있는데','2024-04-03 16:24:59'),(36,1,4,'너무 맛나보여요~~','2024-04-03 16:25:04'),(37,14,4,'맛나보이네요','2024-04-03 16:34:54');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crop_step`
--

DROP TABLE IF EXISTS `crop_step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crop_step` (
  `crop_step_id` int NOT NULL AUTO_INCREMENT,
  `crop_type_id` int NOT NULL,
  `crop_step_growth` int NOT NULL,
  `crop_step_harvest_day` int NOT NULL,
  PRIMARY KEY (`crop_step_id`),
  KEY `crop_type_id` (`crop_type_id`),
  CONSTRAINT `crop_step_ibfk_1` FOREIGN KEY (`crop_type_id`) REFERENCES `crop_type` (`crop_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crop_step`
--

LOCK TABLES `crop_step` WRITE;
/*!40000 ALTER TABLE `crop_step` DISABLE KEYS */;
INSERT INTO `crop_step` VALUES (1,1,1,5),(2,1,2,3),(3,1,3,0),(4,2,1,5),(5,2,2,3),(6,2,3,0),(7,3,1,80),(8,3,2,60),(9,3,3,40),(10,3,4,0),(11,4,1,75),(12,4,2,60),(13,4,3,40),(14,4,4,0),(15,5,1,65),(16,5,2,40),(17,5,3,0);
/*!40000 ALTER TABLE `crop_step` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crop_step_log`
--

DROP TABLE IF EXISTS `crop_step_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crop_step_log` (
  `crop_step_log_id` int NOT NULL AUTO_INCREMENT,
  `pack_diary_id` int NOT NULL,
  `crop_step_id` int NOT NULL,
  `crop_step_log_updated_at` timestamp NOT NULL,
  PRIMARY KEY (`crop_step_log_id`),
  KEY `pack_diary_id` (`pack_diary_id`),
  KEY `crop_step_id` (`crop_step_id`),
  CONSTRAINT `crop_step_log_ibfk_1` FOREIGN KEY (`pack_diary_id`) REFERENCES `pack_diary` (`pack_diary_id`),
  CONSTRAINT `crop_step_log_ibfk_2` FOREIGN KEY (`crop_step_id`) REFERENCES `crop_step` (`crop_step_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crop_step_log`
--

LOCK TABLES `crop_step_log` WRITE;
/*!40000 ALTER TABLE `crop_step_log` DISABLE KEYS */;
INSERT INTO `crop_step_log` VALUES (2,3,1,'2024-04-03 09:03:11'),(3,3,3,'2024-04-03 09:04:12'),(4,4,8,'2024-04-03 09:06:16'),(5,4,9,'2024-04-03 09:07:39'),(6,5,15,'2024-04-03 09:08:34'),(7,5,16,'2024-04-03 09:08:51'),(8,5,17,'2024-04-03 09:09:11'),(9,4,10,'2024-04-03 09:09:38'),(10,7,3,'2024-04-03 09:40:25'),(11,8,3,'2024-04-03 09:41:23'),(13,9,3,'2024-04-03 09:51:48'),(15,26,3,'2024-04-03 10:06:45'),(17,37,3,'2024-04-03 15:25:05'),(18,40,1,'2024-04-03 16:08:50'),(19,40,3,'2024-04-03 16:32:38'),(20,41,3,'2024-04-03 16:37:13');
/*!40000 ALTER TABLE `crop_step_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crop_tip`
--

DROP TABLE IF EXISTS `crop_tip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crop_tip` (
  `crop_tip_id` int NOT NULL AUTO_INCREMENT,
  `crop_type_id` int NOT NULL,
  `crop_tip_growth_step` int NOT NULL,
  `crop_tip_content` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`crop_tip_id`),
  KEY `crop_type_id` (`crop_type_id`),
  CONSTRAINT `crop_tip_ibfk_1` FOREIGN KEY (`crop_type_id`) REFERENCES `crop_type` (`crop_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crop_tip`
--

LOCK TABLES `crop_tip` WRITE;
/*!40000 ALTER TABLE `crop_tip` DISABLE KEYS */;
INSERT INTO `crop_tip` VALUES (1,1,0,'씨앗 심기: 씨앗을 하루 정도 물에 불린 후 한 겹으로 얇게 뿌립니다. 분무기로 물을 충분히 뿌려줍니다. 어두운 곳에서 발아를 하므로 그늘에서 싹을 틔워야 합니다.'),(2,1,1,'발아: 검은 비닐에 싸서 하룻밤 정도 두고 물을 뿌려주면 싹이 뜨고 솜털이 나오기 시작합니다.'),(3,1,2,'새싹: 햇빛이 드는 곳으로 장소를 옮깁니다. 햇빛을 보고 자리지 못하면 노란색으로 자라게 됩니다. 뿌리를 내리는 동안 하루에 2~3번 물을 뿌려 거즈가 마르지 않을 정도도 수분을 공급해줍니다. 분무기로 물을 뿌려주고, 뿌리를 내릴 후에는 뿌리가 물에 잠기게 합니다.'),(4,1,3,'수확하기: 일주일 정도 후 무순이 충분히 자라면 뿌리를 잘라내어 수확합니다.'),(5,2,0,'씨앗 심기: 콩을 물에 담가 3~4시간 불립니다. 구멍이 뚫린 그림에 콩을 넣고 검은 천으로 덮어줍니다. 매일 4시간 간격으로 물을 충분히 부어줍니다.\n재배수온이 23℃이상이 될 경우 부패가 발생하기 쉽기 때문에 온도와 수온을 각각 20℃로 유지해 주는 것이 좋습니다.'),(6,2,1,'발아: 뿌리가 뻗어 나옵니다. 하루에 3~4시간 간격으로 한 번에 5~6번 정도 물을 부어줍니다. 하루에 3~5회 정도 반복합니다.'),(7,2,2,'성장: 뿌리가 길게 자랐습니다. 콩나물이 눈에 띄게 많이 자랐다는 것을 확인할 수 있습니다. 물 주는 과정을 계속 반복합니다.'),(8,2,3,'수확하기: 콩에서 콩나물까지 소요기간은 하절기 6~7일, 동절기 7~8일 입니다. 추운 곳에서는 15일까지도 소요되며, 재배 환경경에 따라 기간이 조금씩 차이 날 수 있습니다.'),(9,3,0,'씨앗 심기: 토마토 씨앗을 토양에 심습니다. 보통 봄이나 초기 여름에 심기 시작하며, 씨앗을 토양에 심을 때는 충분한 햇빛을 받을 수 있는 장소를 선택해야 합니다. 토마토는 밤의 기온이 20℃ 이상이거나 13℃ 이하이면 낙과나 열과 또는 기형이 발생할 수 있습니다. 햇빛을 좋아해 햇볕이 강한 시기에 재배하기 좋습니다. 토마토를 심기 전 재배 일정과 함께 재배온도가 낮 25∼30℃, 밤 18∼20℃가 되는지 확인합니다.'),(10,3,1,'토마토 모종: 토마토 씨앗을 파종해 기르는 것은 수확까지 많은 시간이 소요되기 때문에 시중에 나온 모종을 구입해 심는 것이 좋습니다. 잎이 깨끗하고, 생기가 있는 모종을 선택합니다. 뿌리 부분의 모판흙이 부서지지 않을 정도로 뿌리가 하얗게 잘 발달한 모종을 고릅니다. 물 관리가 안 되어 많이 시들어 있거나 뿌리 부분의 모판흙이 바짝 말라 있는 모종, 너무 춥거나 더운 곳에 있어서 시들어 있는 모종은 스트레스를 받았을 수 있으므로 이점에 유의합니다. 싹이 자랄수록 잎이 나타나고 줄기가 강해집니다. 물은 2~3일 간격으로 적당량을 주는 것이 좋다. 뿌리를 잡은 토마토는 하루가 다르게 성장합니다. 곁가지 제거가 중요한데 초세가 약할 때는 제거 시기를 다소 늦추고 초세가 강할 때는 빨리 제거합니다. 곁가지 제거는 체내 수분 함량이 많은 오전 중에 하는 것이 좋으며, 가위로 자르면 바이러스 전염 우려가 있으므로 손으로 밀어서 한 번에 따줍니다.'),(11,3,2,'토마토 꽃: 토마토가 성장하면서 꽃이 피기 시작합니다. 이는 토마토가 열매를 맺을 준비가 된 것을 나타냅니다.'),(12,3,3,'열매: 작은 토마토가 맺힙니다. 초록색을 띄며 갈 수록 붉게 변합니다.'),(13,3,4,'수확하기: 토마토는 익을수록 붉은색을 띠며 먹음직스럽게 변합니다. 텃밭이 집과 가깝다면 이러한 토마토를 수확하면 됩니다. 텃밭이 멀어 자주 들리지 못한다면 붉은 기운이 조금 감도는 정도의 덜 익은 토마토를 수확하는 게 좋습니다. 덜 익은 토마토를 수확해 집에서 2~3일간 숙성시키면 맛 좋은 토마토를 맛볼 수 있습니다.'),(14,4,0,'감자 심기: 감자종자를 토양에 심습니다. 감자품종은 숙기에 따라 조생종, 중생종, 만생종으로 구분할 수 있습니다. 조생종은 보통 생육기간이 80~95일, 중생종은 95~110일, 만생종은 110일 이상입니다. 우리나라는 사계절이 뚜렷한 기후특성상 고랭지 여름재배와 겨울 시설재배를 제외하고는 남작, 수미, 조풍, 추백 가원, 추동, 조원, 가황, 고운, 새봉, 방울 등 조생종 품종이 적합하며 가장 많이 재배됩니다. 감자 봄 재배는 싹틔움 방식이 적합합니다. 싹틔움상 설치는 예정일로부터 약 20~25일 전에 합니다.'),(15,4,1,'감자 싹: 감자를 심고 20~30일이면 감자싹이 나오기 시작합니다. 감자는 다른 작물에 비해 물을 많이 필요로 하는 작물은 아니지만 감자를 심은 후 싹이 올라올 때와 꽃이 피는 시기에는 물을 충분히 주는 것이 좋습니다.'),(16,4,2,'성장: 감자싹이 5cm 정도 자라면 비닐 안에 흙을 충분히 채워주고 구멍을 흙으로 메워 주어 잡초가 자라지 못하고 감자가 앉을 수 있는 공간을 확보해 주어야 합니다. 멀칭하지 않은 경우 감자싹이 10cm 정도 자랐을 때 1차 북주기를 하고 10~15일쯤 지나 한 번 더 북을 줍니다. 북을 줌으로서 풀을 제거하고 땅 속에 감자가 자랄 수 있는 공간을 확보할 수 있습니다. 비닐 멀칭을 한 경우 감자싹이 나올 때 구멍을 넓혀 주어 감자싹이 밖으로 완전히 바져 나올 수 있도록 합니다.'),(17,4,3,'감자 꽃: 감자 꽃이 피면서 감자 알이 굵어집니다. 물을 많이 필요로 하는 시기입니다.'),(18,4,4,'수확하기: 감자잎이 누렇게 마르는 황엽기에서 줄기가 말라죽은 고엽기사이입니다. 보통 6월 하순~7월 상순에 감자를 수확합니다. 수확한 감자는 그늘에 말리면서 썩거나 병근 감자를 골라낸 후 저장해야 합니다. 일주일 정도 바람이 잘 통하고 어두운 곳에서 잘 말린 후 냉장고에 저장하거나 빛이 들지 않아 서늘한 곳, 밀폐되지 않은 곳에 두고 저장합니다.'),(19,5,0,'씨앗 심기: 줄 간격 10cm~15cm로 줄을 긋고 줄파종을 합니다. 깊이는 씨앗이 살짝 덮일 정도로만 흙을 덮어줍니다. 상추 씨앗은 광발아성이기 때문에 너무 깊게 심으면 발아가 안되거나 오래 걸립니다. 또한, 햇빛이 잘 드는 곳에 심어야 발아도 잘되고, 웃자람 없이 잘 큽니다.'),(20,5,1,'발아: 2~3회 걸쳐 포기 간 간격 10~15cm에 맞게 솎음을 해줍니다. 발아한 대로 모두 키우게 되면 웃자람으로 부실하게 되고 병충해에도 취약하게 됩니다.'),(21,5,2,'새싹(모종): 상추가 어느 정도 성장한 상태입니다. 주말농장이나 작은 텃밭에서는 씨앗보다 모종 심기를 하는 것이 좋습니다. 생육 적온이 15~20℃이기 때문에 초반 성장이 더딜지 몰라도 한 번 자라기 시작하면 금방 자랍니다. 모종을 심을 때 포기 간 10~15cm 간격으로 구멍을 뚫고 물을 흠뻑 준 후에 포트에서 모종을 빼내어 구멍에 넣어줍니다.'),(22,5,3,'수확하기: 씨앗을 파종했다면 솎음이 필요합니다. 솎음은 한 번에 끝내는 것이 아니라 본잎이 2~3개 정도 되었을 때 1차로 시작해서 2~3회 걸쳐서 포기 간격이 될 때까지 해줍니다. 상추 모종을 심고 한 달가량 되면 첫 수확을 할 수 있습니다. 뿌리째 수확하지 않고 잎을 수확합니다. 상추 따는 법은 큰 겉잎을 줄기에서 가깝게 톡 따주면 됩니다.');
/*!40000 ALTER TABLE `crop_tip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crop_type`
--

DROP TABLE IF EXISTS `crop_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crop_type` (
  `crop_type_id` int NOT NULL AUTO_INCREMENT,
  `crop_type_name` varchar(30) NOT NULL,
  `crop_type_img_url` varchar(255) NOT NULL,
  PRIMARY KEY (`crop_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crop_type`
--

LOCK TABLES `crop_type` WRITE;
/*!40000 ALTER TABLE `crop_type` DISABLE KEYS */;
INSERT INTO `crop_type` VALUES (1,'무순','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/radish_sprouts.jpg'),(2,'콩나물','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/sprouts.png'),(3,'토마토','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/tomato.jpg'),(4,'감자','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/potato.jpg'),(5,'상추','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/lecttuce.png');
/*!40000 ALTER TABLE `crop_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deal`
--

DROP TABLE IF EXISTS `deal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deal` (
  `deal_id` int NOT NULL AUTO_INCREMENT,
  `ex_article_id` int DEFAULT NULL,
  `deal_cur_price` int DEFAULT NULL,
  PRIMARY KEY (`deal_id`),
  KEY `ex_article_id` (`ex_article_id`),
  CONSTRAINT `deal_ibfk_1` FOREIGN KEY (`ex_article_id`) REFERENCES `ex_article` (`ex_article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deal`
--

LOCK TABLES `deal` WRITE;
/*!40000 ALTER TABLE `deal` DISABLE KEYS */;
INSERT INTO `deal` VALUES (1,1,1112),(2,4,999),(5,8,500),(8,12,5100),(26,38,1104);
/*!40000 ALTER TABLE `deal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diary`
--

DROP TABLE IF EXISTS `diary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diary` (
  `diary_id` int NOT NULL AUTO_INCREMENT,
  `pack_diary_id` int NOT NULL,
  `diary_behavior` varchar(10) NOT NULL,
  `diary_title` varchar(60) DEFAULT NULL,
  `diary_content` varchar(1000) DEFAULT NULL,
  `diary_selected_at` date NOT NULL,
  `diary_created_at` timestamp NOT NULL,
  PRIMARY KEY (`diary_id`),
  KEY `pack_diary_id` (`pack_diary_id`),
  CONSTRAINT `diary_ibfk_1` FOREIGN KEY (`pack_diary_id`) REFERENCES `pack_diary` (`pack_diary_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diary`
--

LOCK TABLES `diary` WRITE;
/*!40000 ALTER TABLE `diary` DISABLE KEYS */;
INSERT INTO `diary` VALUES (2,2,'다이어리','우리 상추가 싹이 텄네','귀여운 울 상추 드디어 싹이 텄어요!!','2024-04-03','2024-04-03 02:09:37'),(3,3,'다이어리','무순이 첫 씨앗 뿌린날❣️','오늘 무순 씨앗을 처음으로 뿌렸다?\r\n빨리 쑥쑥 자라줬으면 좋겠다 :)','2024-03-30','2024-04-03 02:12:32'),(4,3,'다이어리','무순이 첫 씨앗 뿌린날❣️','오늘 무순 씨앗을 처음으로 뿌렸다?\r\n빨리 쑥쑥 자라줬으면 좋겠다 :)','2024-03-30','2024-04-03 02:12:42'),(5,3,'다이어리','무순 벌써 발아?','하루밖에 안지났는데 무순이 벌써 발아했다?\r\n너무 신기하다!!','2024-03-31','2024-04-03 02:15:49'),(6,4,'다이어리','토마토 모종 심기?','오늘 토마토 모종을 텃밭에 심었다!\r\n토마토 키우기는 처음이라 잘 자라주었으면 좋겠다❤️','2024-04-01','2024-04-03 02:52:20'),(7,4,'물주기',NULL,NULL,'2024-04-01','2024-04-03 02:52:28'),(9,3,'물주기',NULL,NULL,'2024-04-01','2024-04-03 02:53:13'),(16,9,'다이어리','수확','수확 후 글 올리기','2024-04-03','2024-04-03 09:42:02'),(24,35,'다이어리','오늘의 작물일기','전화위복','2024-04-03','2024-04-03 12:12:22'),(25,35,'물주기',NULL,NULL,'2024-04-03','2024-04-03 12:12:48'),(28,37,'다이어리','무수니와 첫만남','오늘은 무수니를 처음 심은 날이다. 성심성의껏 키워줘야지~~','2024-04-03','2024-04-03 15:23:53'),(29,37,'물주기',NULL,NULL,'2024-04-03','2024-04-03 15:24:04'),(30,40,'다이어리','무순아 무순아','무순 2일차!!','2024-03-30','2024-04-03 16:00:51'),(31,40,'다이어리','이제 거의 다 자랐다','곧 수확할 수 있겠다??','2024-04-01','2024-04-03 16:01:38'),(32,40,'물주기',NULL,NULL,'2024-03-29','2024-04-03 16:04:10'),(33,40,'물주기',NULL,NULL,'2024-03-30','2024-04-03 16:04:15'),(34,40,'물주기',NULL,NULL,'2024-03-31','2024-04-03 16:04:20'),(35,40,'물주기',NULL,NULL,'2024-04-01','2024-04-03 16:04:25'),(36,40,'물주기',NULL,NULL,'2024-04-02','2024-04-03 16:04:29'),(39,41,'다이어리','벌써 이렇게 컷어??','조만간 수확해도 되겠다!\r\n','2024-04-03','2024-04-03 16:35:45'),(40,41,'물주기',NULL,NULL,'2024-04-03','2024-04-03 16:35:49');
/*!40000 ALTER TABLE `diary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_alarm`
--

DROP TABLE IF EXISTS `event_alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_alarm` (
  `alarm_id` bigint NOT NULL AUTO_INCREMENT,
  `receive_user_id` int NOT NULL,
  `send_user_id` int NOT NULL,
  `event_alarm_type` varchar(20) NOT NULL,
  `event_alarm_content` varchar(200) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `event_alarm_is_checked` tinyint(1) DEFAULT '0',
  `event_alarm_redirect` varchar(255) NOT NULL,
  PRIMARY KEY (`alarm_id`),
  KEY `receive_user_id` (`receive_user_id`),
  KEY `send_user_id` (`send_user_id`),
  CONSTRAINT `event_alarm_ibfk_1` FOREIGN KEY (`receive_user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `event_alarm_ibfk_2` FOREIGN KEY (`send_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_alarm`
--

LOCK TABLES `event_alarm` WRITE;
/*!40000 ALTER TABLE `event_alarm` DISABLE KEYS */;
INSERT INTO `event_alarm` VALUES (1,4,2,'작물거래','유지민님이 #테스트 입니다#에 가격을 제안하셨어요.','2024-04-03 02:15:38',0,'/trade/5/seller'),(2,1,3,'작물거래','김민지님이 #맛있는 토마토 팔아요 제안 주세요 ?? #에 가격을 제안하셨어요.','2024-04-03 02:16:16',1,'/trade/4/seller'),(3,1,3,'작물거래','김민지님이 #맛있는 토마토 팔아요 제안 주세요 ?? #에 가격을 제안하셨어요.','2024-04-03 02:16:47',1,'/trade/4/seller'),(4,3,1,'작물거래','장원영님이 #맛있는 토마토 팔아요 제안 주세요 ?? #에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 02:17:02',0,'/trade/1/chat'),(5,4,1,'작물거래','장원영님이 #테스트입니다람쥐#에 가격을 제안하셨어요.','2024-04-03 02:18:33',0,'/trade/7/seller'),(6,4,1,'작물거래','장원영님이 #테스트입니다람쥐#에 가격을 제안하셨어요.','2024-04-03 02:18:38',0,'/trade/7/seller'),(7,3,1,'작물거래','장원영님이 #싱싱한 무순 팝니다❣️#에 가격을 제안하셨어요.','2024-04-03 02:21:14',0,'/trade/8/seller'),(8,1,3,'작물거래','김민지님이 #싱싱한 무순 팝니다❣️#에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 02:21:26',1,'/trade/2/chat'),(9,1,2,'커뮤니티','유지민님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 02:24:29',1,'/community/1'),(10,1,1,'커뮤니티','장원영님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 02:25:23',1,'/community/1'),(11,3,1,'커뮤니티','장원영님이 #?제가 키운 무순을 소개합니다?#에 댓글을 달았어요!','2024-04-03 02:25:25',0,'/community/2'),(12,3,1,'커뮤니티','장원영님이 #?제가 키운 무순을 소개합니다?#에 댓글을 달았어요!','2024-04-03 02:25:51',0,'/community/2'),(13,1,2,'커뮤니티','유지민님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 02:26:08',1,'/community/1'),(14,4,3,'작물거래','김민지님이 #테스트입니다람쥐#에 가격을 제안하셨어요.','2024-04-03 02:29:51',0,'/trade/7/seller'),(15,1,3,'커뮤니티','김민지님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 02:31:06',1,'/community/1'),(16,4,2,'작물거래','유지민님이 #테스트입니다람쥐#에 가격을 제안하셨어요.','2024-04-03 02:32:49',0,'/trade/7/seller'),(17,4,2,'작물거래','유지민님이 #테스트입니다람쥐#에 가격을 제안하셨어요.','2024-04-03 02:33:08',0,'/trade/7/seller'),(18,4,2,'작물거래','유지민님이 #테스트입니다람쥐#에 가격을 제안하셨어요.','2024-04-03 02:33:23',0,'/trade/7/seller'),(19,4,3,'작물거래','김민지님이 #테스트입니다람쥐#에 가격을 제안하셨어요.','2024-04-03 02:37:30',0,'/trade/7/seller'),(20,3,4,'작물거래','오해원님이 #테스트입니다람쥐#에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 02:37:37',0,'/trade/3/chat'),(21,4,3,'작물거래','김민지님이 #⏰ 마감 임박 ⏰ 상큼한 레몬 가져가세요~~#에 채팅을 보냈어요!','2024-04-03 03:02:23',0,'/trade/4/chat'),(22,1,1,'커뮤니티','장원영님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 09:11:49',1,'/community/1'),(23,1,1,'커뮤니티','장원영님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 09:13:24',1,'/community/1'),(24,1,1,'커뮤니티','장원영님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 09:13:54',1,'/community/1'),(25,1,8,'작물거래','이혜인님이 #맛있는 토마토 팔아요 제안 주세요 ?? #에 가격을 제안하셨어요.','2024-04-03 09:27:47',0,'/trade/4/seller'),(26,7,2,'작물거래','유지민님이 #토마토 팝니다❤#에 가격을 제안하셨어요.','2024-04-03 09:28:19',0,'/trade/12/seller'),(27,2,7,'작물거래','해린님이 #토마토 팝니다❤#에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 09:28:25',1,'/trade/5/chat'),(28,7,2,'작물거래','유지민님이 #상추 팝니다!#에 가격을 제안하셨어요.','2024-04-03 09:30:18',0,'/trade/13/seller'),(29,2,7,'작물거래','해린님이 #상추 팝니다!#에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 09:38:08',0,'/trade/6/chat'),(30,3,2,'커뮤니티','유지민님이 #무순을 올린 육회비빔밥?#에 댓글을 달았어요!','2024-04-03 09:41:13',0,'/community/4'),(31,2,3,'작물거래','김민지님이 #거래 테스트#에 채팅을 보냈어요!','2024-04-03 09:42:29',1,'/trade/7/chat'),(32,8,1,'작물거래','장원영님이 #맛있는 토마토 팔아요 제안 주세요 ?? #에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 09:45:15',0,'/trade/8/chat'),(33,2,3,'작물거래','김민지님이 #거래 테스트#에 채팅을 보냈어요!','2024-04-03 09:46:26',1,'/trade/9/chat'),(34,1,8,'작물거래','이혜인님이 #맛있는 토마토 팔아요 제안 주세요 ?? #에 가격을 제안하셨어요.','2024-04-03 09:47:11',0,'/trade/4/seller'),(35,7,8,'작물거래','이혜인님이 #토마토 팝니다❤#에 가격을 제안하셨어요.','2024-04-03 09:47:29',0,'/trade/12/seller'),(36,2,3,'작물거래','김민지님이 #일반 거래 테스트#에 채팅을 보냈어요!','2024-04-03 09:47:43',0,'/trade/10/chat'),(37,1,1,'커뮤니티','장원영님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 09:48:13',0,'/community/1'),(38,1,1,'커뮤니티','장원영님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 09:48:14',0,'/community/1'),(39,1,1,'커뮤니티','장원영님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 09:48:14',0,'/community/1'),(40,1,1,'커뮤니티','장원영님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 09:48:14',0,'/community/1'),(41,1,1,'커뮤니티','장원영님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 09:48:17',0,'/community/1'),(42,3,7,'작물거래','해린님이 #거래 테스트#에 가격을 제안하셨어요.','2024-04-03 09:49:31',0,'/trade/15/seller'),(43,3,3,'커뮤니티','김민지님이 #c102 화이팅!#에 댓글을 달았어요!','2024-04-03 09:51:32',0,'/community/10'),(44,3,2,'작물거래','유지민님이 #test#에 채팅을 보냈어요!','2024-04-03 09:54:06',0,'/trade/11/chat'),(45,1,1,'커뮤니티','장원영님이 #1111111888ㅑㅑㅑㅑㅑㅑㅑㅑ으으으으ㅡ으으으으ㅏㅏㅏ#에 댓글을 달았어요!','2024-04-03 09:55:44',0,'/community/13'),(46,1,1,'커뮤니티','장원영님이 #1111111888ㅑㅑㅑㅑㅑㅑㅑㅑ으으으으ㅡ으으으으ㅏㅏㅏ#에 댓글을 달았어요!','2024-04-03 09:55:46',0,'/community/13'),(47,1,1,'커뮤니티','장원영님이 #1111111888ㅑㅑㅑㅑㅑㅑㅑㅑ으으으으ㅡ으으으으ㅏㅏㅏ#에 댓글을 달았어요!','2024-04-03 09:55:47',0,'/community/13'),(48,1,1,'커뮤니티','장원영님이 #1111111888ㅑㅑㅑㅑㅑㅑㅑㅑ으으으으ㅡ으으으으ㅏㅏㅏ#에 댓글을 달았어요!','2024-04-03 09:55:55',0,'/community/13'),(49,1,4,'커뮤니티','오해원님이 #1111111888ㅑㅑㅑㅑㅑㅑㅑㅑ으으으으ㅡ으으으으ㅏㅏㅏ#에 댓글을 달았어요!','2024-04-03 09:56:25',0,'/community/13'),(50,3,2,'작물거래','유지민님이 #test2#에 채팅을 보냈어요!','2024-04-03 09:56:37',0,'/trade/12/chat'),(51,1,1,'커뮤니티','장원영님이 #1111111888ㅑㅑㅑㅑㅑㅑㅑㅑ으으으으ㅡ으으으으ㅏㅏㅏ#에 댓글을 달았어요!','2024-04-03 09:56:40',0,'/community/13'),(52,3,2,'작물거래','유지민님이 #거래테스트3#에 채팅을 보냈어요!','2024-04-03 09:56:48',0,'/trade/13/chat'),(53,2,3,'작물거래','김민지님이 #테스트#에 채팅을 보냈어요!','2024-04-03 09:58:18',1,'/trade/14/chat'),(54,1,3,'커뮤니티','김민지님이 #1111111888ㅑㅑㅑㅑㅑㅑㅑㅑ으으으으ㅡ으으으으ㅏㅏㅏ#에 댓글을 달았어요!','2024-04-03 10:00:08',0,'/community/13'),(55,3,1,'커뮤니티','ㅁㅁㅁㅁ님이 #무순을 올린 육회비빔밥?#에 댓글을 달았어요!','2024-04-03 10:01:56',0,'/community/4'),(56,3,1,'커뮤니티','ㅁㅁㅁㅁ님이 #무순을 올린 육회비빔밥?#에 댓글을 달았어요!','2024-04-03 10:02:00',0,'/community/4'),(57,1,1,'커뮤니티','ㅁㅁㅁㅁ님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 10:03:12',0,'/community/1'),(58,1,3,'작물거래','김민지님이 #제안입니다#에 가격을 제안하셨어요.','2024-04-03 10:05:08',0,'/trade/24/seller'),(59,2,1,'커뮤니티','ㅁㅁㅁㅁ님이 #요리#에 댓글을 달았어요!','2024-04-03 10:06:44',0,'/community/12'),(60,8,3,'작물거래','김민지님이 #상추 판매합니다#에 가격을 제안하셨어요.','2024-04-03 10:07:36',0,'/trade/25/seller'),(61,8,3,'작물거래','김민지님이 #상추 판매합니다#에 가격을 제안하셨어요.','2024-04-03 10:07:47',0,'/trade/25/seller'),(62,8,3,'작물거래','김민지님이 #상추 판매합니다#에 가격을 제안하셨어요.','2024-04-03 10:07:53',0,'/trade/25/seller'),(63,3,8,'작물거래','이혜인님이 #상추 판매합니다#에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 10:08:07',0,'/trade/15/chat'),(64,3,1,'작물거래','ㅁㅁㅁㅁ님이 #제안테스트#에 가격을 제안하셨어요.','2024-04-03 10:17:03',0,'/trade/26/seller'),(65,1,7,'작물거래','해린님이 #제안입니다#에 가격을 제안하셨어요.','2024-04-03 10:30:12',0,'/trade/24/seller'),(66,7,1,'작물거래','ㅁㅁㅁㅁ님이 #토마토 팝니다❤#에 가격을 제안하셨어요.','2024-04-03 10:31:50',0,'/trade/12/seller'),(67,1,7,'작물거래','해린님이 #토마토 팝니다❤#에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 10:31:56',0,'/trade/16/chat'),(68,8,7,'작물거래','해린님이 #상추 판매합니다#에 가격을 제안하셨어요.','2024-04-03 10:32:46',0,'/trade/25/seller'),(69,8,4,'작물거래','오해원님이 #상추 판매합니다#에 가격을 제안하셨어요.','2024-04-03 10:43:57',0,'/trade/25/seller'),(70,3,1,'작물거래','ㅁㅁㅁㅁ님이 #제안테스트#에 가격을 제안하셨어요.','2024-04-03 10:56:13',0,'/trade/26/seller'),(71,8,4,'작물거래','오해원님이 #상추 판매합니다#에 가격을 제안하셨어요.','2024-04-03 11:01:29',0,'/trade/25/seller'),(72,8,7,'작물거래','해린님이 #토마토 팝니다❤#에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 11:05:09',0,'/trade/17/chat'),(73,1,4,'작물거래','오해원님이 #정신차려#에 가격을 제안하셨어요.','2024-04-03 11:07:14',0,'/trade/27/seller'),(74,4,1,'작물거래','ㅁㅁㅁㅁ님이 #정신차려#에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 11:07:17',0,'/trade/18/chat'),(76,7,1,'작물거래','ㅁㅁㅁㅁ님이 #맛있는 토마토 팝니다?#에 가격을 제안하셨어요.','2024-04-03 11:34:47',0,'/trade/28/seller'),(77,7,1,'작물거래','ㅁㅁㅁㅁ님이 #맛있는 토마토 팝니다?#에 가격을 제안하셨어요.','2024-04-03 11:34:52',0,'/trade/28/seller'),(78,2,1,'작물거래','ㅁㅁㅁㅁ님이 #상추 판매합니다#에 가격을 제안하셨어요.','2024-04-03 11:35:59',0,'/trade/1/seller'),(79,2,1,'작물거래','ㅁㅁㅁㅁ님이 #상추 판매합니다#에 가격을 제안하셨어요.','2024-04-03 11:36:01',0,'/trade/1/seller'),(80,1,8,'작물거래','이혜인님이 #갓 수확한 당근 팔아요#에 채팅을 보냈어요!','2024-04-03 11:47:03',0,'/trade/19/chat'),(81,2,1,'작물거래','ㅁㅁㅁㅁ님이 #거래 테스트#에 채팅을 보냈어요!','2024-04-03 11:51:16',0,'/trade/20/chat'),(82,8,1,'작물거래','ㅁㅁㅁㅁ님이 #상추 판매합니다#에 가격을 제안하셨어요.','2024-04-03 11:52:09',0,'/trade/25/seller'),(83,7,1,'작물거래','ㅁㅁㅁㅁ님이 #제안입니다#에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 11:52:48',0,'/trade/21/chat'),(84,3,1,'작물거래','ㅁㅁㅁㅁ님이 #제안입니다#에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 11:52:54',0,'/trade/22/chat'),(85,2,1,'작물거래','ㅁㅁㅁㅁ님이 #상추 팔아요#에 가격을 제안하셨어요.','2024-04-03 12:00:05',0,'/trade/23/seller'),(86,7,1,'작물거래','ㅁㅁㅁㅁ님이 #상추 팝니다!#에 가격을 제안하셨어요.','2024-04-03 12:02:38',0,'/trade/13/seller'),(87,1,4,'작물거래','오해원님이 #? 옥수수 나눠드릴게요 ~ ?#에 채팅을 보냈어요!','2024-04-03 14:15:42',0,'/trade/23/chat'),(88,2,1,'커뮤니티','ㅁㅁㅁㅁ님이 #요리#에 댓글을 달았어요!','2024-04-03 14:16:01',0,'/community/12'),(89,4,9,'작물거래','황솔촌님이 #둘이 먹다 셋이 죽어도 모르는 황금사과 팔아요#에 채팅을 보냈어요!','2024-04-03 14:24:38',0,'/trade/24/chat'),(90,1,9,'작물거래','황솔촌님이 #제안입니다#에 가격을 제안하셨어요.','2024-04-03 14:27:21',0,'/trade/24/seller'),(91,9,1,'작물거래','서현진님이 #맛있는 귤#에 가격을 제안하셨어요.','2024-04-03 14:37:34',0,'/trade/31/seller'),(92,9,1,'작물거래','서현진님이 #맛있는 귤#에 가격을 제안하셨어요.','2024-04-03 14:37:42',0,'/trade/31/seller'),(93,1,9,'작물거래','황솔촌님이 #맛있는 귤#에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 14:37:50',0,'/trade/25/chat'),(94,9,1,'작물거래','서현진님이 #맛있는 귤#에 가격을 제안하셨어요.','2024-04-03 14:38:11',0,'/trade/31/seller'),(95,9,1,'작물거래','서현진님이 #맛있는 귤#에 가격을 제안하셨어요.','2024-04-03 14:38:19',1,'/trade/31/seller'),(96,9,1,'작물거래','서현진님이 #맛있는 귤#에 가격을 제안하셨어요.','2024-04-03 14:38:30',1,'/trade/31/seller'),(97,2,9,'커뮤니티','황솔촌님이 #요리#에 댓글을 달았어요!','2024-04-03 14:43:09',0,'/community/12'),(98,1,4,'커뮤니티','오해원님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 14:58:19',0,'/community/1'),(99,1,4,'커뮤니티','오해원님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 14:58:29',0,'/community/1'),(100,1,4,'커뮤니티','오해원님이 #우리집 고양이 츄르를 좋아해#에 댓글을 달았어요!','2024-04-03 14:58:42',0,'/community/1'),(101,5,3,'작물거래','김민지님이 #구글- 알림테스트#에 가격을 제안하셨어요.','2024-04-03 14:59:25',0,'/trade/32/seller'),(102,5,3,'작물거래','김민지님이 #구글- 알림테스트#에 가격을 제안하셨어요.','2024-04-03 14:59:45',0,'/trade/32/seller'),(103,5,3,'작물거래','김민지님이 #구글- 알림테스트#에 가격을 제안하셨어요.','2024-04-03 15:00:01',0,'/trade/32/seller'),(104,3,5,'작물거래','오정민-google님이 #채팅 테스트#에 채팅을 보냈어요!','2024-04-03 15:00:52',0,'/trade/26/chat'),(105,5,3,'작물거래','김민지님이 #구글 테스트#에 가격을 제안하셨어요.','2024-04-03 15:01:25',0,'/trade/34/seller'),(106,5,3,'작물거래','김민지님이 #구글 테스트#에 가격을 제안하셨어요.','2024-04-03 15:01:34',0,'/trade/34/seller'),(107,1,5,'작물거래','오정민-google님이 #? 옥수수 나눠드릴게요 ~ ?#에 채팅을 보냈어요!','2024-04-03 15:04:13',0,'/trade/27/chat'),(108,4,13,'작물거래','고두심님이 #둘이 먹다 셋이 죽어도 모르는 황금사과 팔아요#에 채팅을 보냈어요!','2024-04-03 15:27:07',0,'/trade/28/chat'),(109,7,13,'작물거래','고두심님이 #토마토 팝니다❤#에 가격을 제안하셨어요.','2024-04-03 15:28:08',0,'/trade/12/seller'),(110,7,1,'작물거래','서현진님이 #토마토 팝니다❤#에 가격을 제안하셨어요.','2024-04-03 15:29:06',0,'/trade/12/seller'),(111,9,13,'커뮤니티','고두심님이 #만나서 반가워요#에 댓글을 달았어요!','2024-04-03 15:30:58',0,'/community/14'),(112,7,5,'작물거래','오정민-google님이 #상추 팝니다!#에 가격을 제안하셨어요.','2024-04-03 15:46:36',0,'/trade/13/seller'),(113,7,1,'작물거래','수근농부님이 #토마토 팝니다❤#에 가격을 제안하셨어요.','2024-04-03 16:20:36',0,'/trade/12/seller'),(114,3,1,'커뮤니티','수근농부님이 #무순을 올린 육회비빔밥?#에 댓글을 달았어요!','2024-04-03 16:24:59',0,'/community/4'),(115,3,1,'커뮤니티','수근농부님이 #무순을 올린 육회비빔밥?#에 댓글을 달았어요!','2024-04-03 16:25:04',0,'/community/4'),(116,7,14,'작물거래','신동근[광주_1반_C107]-google님이 #상추 팝니다!#에 가격을 제안하셨어요.','2024-04-03 16:33:13',0,'/trade/13/seller'),(117,3,14,'커뮤니티','안녕씅ㅇㅇㅇㅇㅇㅇ님이 #무순을 올린 육회비빔밥?#에 댓글을 달았어요!','2024-04-03 16:34:54',0,'/community/4'),(118,1,2,'작물거래','여름쿨톤님이 #판교에서 자란 무순이 드실분~~#에 가격을 제안하셨어요.','2024-04-03 16:48:22',0,'/trade/37/seller'),(119,1,2,'작물거래','여름쿨톤님이 #판교에서 자란 무순이 드실분~~#에 가격을 제안하셨어요.','2024-04-03 16:51:20',0,'/trade/38/seller'),(120,2,1,'작물거래','수근농부님이 #판교에서 자란 무순이 드실분~~#에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 16:51:30',0,'/trade/29/chat'),(121,1,9,'작물거래','황솔촌님이 #판교에서 자란 무순이 드실분~~#에 가격을 제안하셨어요.','2024-04-03 17:40:43',0,'/trade/38/seller'),(122,1,9,'작물거래','황솔촌님이 #판교에서 자란 무순이 드실분~~#에 가격을 제안하셨어요.','2024-04-03 17:40:54',0,'/trade/38/seller'),(123,1,9,'작물거래','황솔촌님이 #판교에서 자란 무순이 드실분~~#에 가격을 제안하셨어요.','2024-04-03 17:41:20',0,'/trade/38/seller'),(124,1,9,'작물거래','황솔촌님이 #판교에서 자란 무순이 드실분~~#에 가격을 제안하셨어요.','2024-04-03 17:44:56',0,'/trade/38/seller'),(125,9,1,'작물거래','수근농부님이 #판교에서 자란 무순이 드실분~~#에 입찰한 당신의 제안에 관심을 보였어요!','2024-04-03 17:45:17',0,'/trade/30/chat'),(126,7,8,'작물거래','이혜인님이 #토마토 팝니다❤#에 가격을 제안하셨어요.','2024-04-04 08:53:20',0,'/trade/12/seller');
/*!40000 ALTER TABLE `event_alarm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_article`
--

DROP TABLE IF EXISTS `ex_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ex_article` (
  `ex_article_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `ex_article_title` varchar(255) NOT NULL,
  `ex_article_content` varchar(2000) NOT NULL,
  `ex_article_location` varchar(255) NOT NULL,
  `ex_article_place` varchar(100) DEFAULT NULL,
  `ex_article_type` varchar(20) NOT NULL,
  `ex_article_is_done` tinyint(1) NOT NULL,
  `ex_article_purchaser_id` int DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `pack_diary_id` int DEFAULT NULL,
  PRIMARY KEY (`ex_article_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `ex_article_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_article`
--

LOCK TABLES `ex_article` WRITE;
/*!40000 ALTER TABLE `ex_article` DISABLE KEYS */;
INSERT INTO `ex_article` VALUES (1,2,'상추 판매합니다','싱싱한 상추팝니다','광주 광산구 장덕동','목련마을 2단지 105동 앞','DEAL',0,NULL,'2024-04-03 02:02:18',NULL),(2,1,'갓 수확한 당근 팔아요','텃밭에서 사랑으로 기른 당근 팝니다 ','광주 광산구 장덕동','고실중학교 앞','GENERAL_TRANSACTION',1,8,'2024-04-03 11:04:12',NULL),(3,4,'둘이 먹다 셋이 죽어도 모르는 황금사과 팔아요','아침에 먹는 사과는 금인거 아시죠? 꿀맛 사과 팔아요!!','광주 광산구 장덕동','장인족발 앞','GENERAL_TRANSACTION',0,NULL,'2024-04-03 11:06:16',NULL),(4,1,'맛있는 토마토 팔아요 제안 주세요 ?? ','??? 맛있는 토마토 ???','광주 광산구 장덕동','다이소 앞 사거리','DEAL',1,8,'2024-04-03 11:10:49',NULL),(6,1,'? 옥수수 나눠드릴게요 ~ ?','???? 옥수수 드립니다','광주 광산구 장덕동','우리집 앞','SHARING',0,NULL,'2024-04-03 11:15:39',NULL),(8,3,'싱싱한 무순 팝니다❣️','제가 정성들여 키운 싱싱한 무순 팝니다!\n많은 제안 주세요?','광주 광산구 하남동','하남 홈플러스 앞','DEAL',1,1,'2024-04-03 11:20:50',3),(9,4,'⏰ 마감 임박 ⏰ 상큼한 레몬 가져가세요~~','재배한지 얼마 되지 않아 엄청 싱싱하고 매우 셔요!!','광주 광산구 장덕동','얍 피시방 앞','SHARING',1,3,'2024-04-03 11:43:27',NULL),(12,7,'토마토 팝니다❤','제가 키운 싱싱한 토마토 팝니다!!','광주 광산구 오선동','삼성전자 광주 사업장 앞','DEAL',0,NULL,'2024-04-03 18:21:29',6),(17,2,'일반 거래 테스트','아ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ','광주 광산구 오선동','ssafy','GENERAL_TRANSACTION',1,3,'2024-04-03 18:47:30',NULL),(38,1,'판교에서 자란 무순이 드실분~~','싱싱한 무순이 가져가세요~~','광주 광산구 오선동','역삼 멀티캠퍼스 앞','DEAL',1,9,'2024-04-04 01:51:13',40);
/*!40000 ALTER TABLE `ex_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `farm`
--

DROP TABLE IF EXISTS `farm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `farm` (
  `farm_id` int NOT NULL AUTO_INCREMENT,
  `farm_type` varchar(30) DEFAULT NULL,
  `farm_nm` varchar(255) DEFAULT NULL,
  `farm_area_lcd` int DEFAULT NULL,
  `farm_area_lnm` varchar(15) DEFAULT NULL,
  `farm_area_mcd` int DEFAULT NULL,
  `farm_area_mnm` varchar(30) DEFAULT NULL,
  `farm_address` varchar(255) DEFAULT NULL,
  `farm_off_site` varchar(255) DEFAULT NULL,
  `farm_poslat` float DEFAULT NULL,
  `farm_poslng` float DEFAULT NULL,
  PRIMARY KEY (`farm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=720 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `farm`
--

LOCK TABLES `farm` WRITE;
/*!40000 ALTER TABLE `farm` DISABLE KEYS */;
INSERT INTO `farm` VALUES (1,'개인','천안도시농부텃밭',15,'충청남도',226,'천안시','충남 천안시 동남구 목천읍 동리 147-1','주차장, 화장실, 농기구대여소, 쉼터, 교육장',36.7844,127.241),(2,'개인','용산가족공원텃밭',1,'서울특별시',21,'용산구','서울 용산구 서빙고로 185 (용산동6가, 용산공원)','원두막,연못,농기구대여소,프로그램실',37.5201,126.985),(3,'민간단체','손수레농장',6,'대전광역시',68,'유성구','대전광역시 유성구 덕진동 63','화장실, 샤워실, 실습실',36.4194,127.377),(4,'개인','삼연주말농장',5,'광주광역시',60,'광산구','광산구 삼도동 586','',35.1325,126.687),(5,'지자체/주말농장','천생연분마을',9,'경기도',120,'양주시','경기도 양주시 일영로 502번길 105','화장실,주차장,쉼터, 농기구보관함, 포토존, 키즈존',37.6912,126.93),(6,'개인','햇살가득 파주 주말농장',9,'경기도',NULL,'','파주시 파주읍 봉암리','화장실,오두막,주차장',37.8212,126.796),(8,'개인','만덕주말농장',2,'부산광역시',32,'북구','부산 북구 만덕동 549','파고라, 농막, 화장실, 주차장',35.2173,129.042),(9,'민간단체','보광중학교',10,'경상남도',142,'양산시','경상남도 양산시 하북면 순지리 125-5','',35.492,129.088),(10,'지자체/학교','양주중학교',10,'경상남도',142,'양산시','경상남도 양산시 상북면 석계리 332','',35.4146,129.066),(11,'개인','송제농장',10,'경상남도',142,'양산시','경상남도 양산시 상북면 상삼리 산 26-1','화장실, 용수시설',35.418,129.052),(12,'민간단체','지산마을',10,'경상남도',142,'양산시','경상남도 양산시 하북면 순지리 371','',35.5026,129.082),(13,'개인','답곡마을',10,'경상남도',142,'양산시','경상남도 양산시 하북면 답곡리 778','',35.4794,129.09),(14,'개인','삼감마을',10,'경상남도',142,'양산시','경상남도 양산시 하북면 삼감리 210-4','',35.4486,129.069),(15,'민간단체','양산고등학교',10,'경상남도',142,'양산시','경남 양산시 북부동 255','',35.3488,129.045),(16,'개인','풀과 꽃이야기',10,'경상남도',142,'양산시','경상남도 양산시 상북면 대석리 1079, 273-4','화장실, 쉼터, 용수시설',35.392,129.064),(17,'지자체/주말농장','어울림공간',10,'경상남도',142,'양산시','경상남도 양산시 상북면 소석리 646-4','',35.3983,129.053),(18,'민간단체','주말농장(양산시 상북면)',10,'경상남도',142,'양산시','경상남도 양산시 상북면 석계리 541-10','',35.4097,129.057),(19,'민간단체','원동중학교 텃밭',10,'경상남도',142,'양산시','경상남도 양산시 원동면 원리 577','',35.3756,128.921),(20,'개인','물금주말농장3',10,'경상남도',142,'양산시','경상남도 양산시 물금읍 증산리 559-1','',35.3015,129.005),(21,'지자체/주말농장','문화연구소텃밭놀이터',10,'경상남도',142,'양산시','경상남도 양산시 상북면 대석리 250-2','화장실, 정자, 놀이터, 용수시설',35.3894,129.068),(22,'지자체/개인농장','물금주말농장2',10,'경상남도',142,'양산시','경상남도 양산시 물금읍 증산리 841-2','창고, 용수시설',35.297,129.009),(24,'민간단체','범어주말농장',10,'경상남도',142,'양산시','경상남도 양산시 물금읍 범어리 622','용수시설, 창고',35.3327,129.003),(25,'개인','증산정자나무',10,'경상남도',142,'양산시','경상남도 양산시 물금읍 증산리 104-5외','주차장',35.3032,129.003),(26,'지자체/자율','주말농장(거제 문동동1)',10,'경상남도',137,'거제시','경상남도 거제시 문동동 381','하천',34.8635,128.649),(27,'지자체/자율','주말농장(거제 문동동2)',10,'경상남도',137,'거제시','경상남도 거제시 문동동 295-1','하천',34.8613,128.647),(28,'지자체/자율','주말농장(거제 문동동3)',10,'경상남도',137,'거제시','경상남도 거제시 문동동 295-2','하천',34.861,128.647),(29,'지자체/자율','주말농장(거제 문동동4)',10,'경상남도',137,'거제시','경상남도 거제시 문동동 296-1','하천',34.8615,128.647),(30,'지자체/자율','주말농장(거제 문동동5)',10,'경상남도',137,'거제시','경상남도 거제시 문동동 296-2','하천',34.8612,128.647),(31,'민간단체','용현농협주말농장',10,'경상남도',141,'사천시','경상남도 사천시 용현면 구월리 656','',35.0093,128.068),(32,'지자체/주말농장','진한주말농장',10,'경상남도',141,'사천시','경상남도 사천시 사남면 종천리 550','화장실, 주차장, 쉼터 등',35.0064,128.139),(33,'지자체/주말농장','시민텃밭',10,'경상남도',143,'진주시','경상남도 진주시 상대동 315-3외','',35.1815,128.12),(34,'개인','농촌체험휴양마을',10,'경상남도',145,'창원시','경상남도 창원시 예곡동 3번지 외 3필','간이화장실',35.1631,128.556),(35,'지자체/주말농장','농촌체험휴양마을2',10,'경상남도',145,'창원시','경상남도 창원시 동읍 353번지 외','간이화장실',35.2804,128.658),(36,'개인','농촌체험휴양마을1',10,'경상남도',145,'창원시','경상남도 창원시 북면 507번지','간이화장실',35.2944,128.59),(37,'지자체/주말농장','예천군주말농장',11,'경상북도',174,'예천군','경상북도 예천군 호명면 631-5','',36.5587,128.46),(38,'민간단체','무명텃밭(구미 임은동 코오롱하늘채)',11,'경상북도',159,'구미시','경상북도 구미시 임은동 174','',36.0855,128.368),(39,'개인','구미주말농장',11,'경상북도',159,'구미시','경상북도 구미시 지산동 846-52','',36.1363,128.362),(40,'지자체/주말농장','한평농원',11,'경상북도',160,'김천시','경상북도 김천시 남면 옥산리 637','주차장, 화장실',36.1161,128.19),(41,'지자체/주말농장','한평농원',11,'경상북도',160,'김천시','경상북도 김천시 구성면 감천대로 3296','주차장, 쉼터',36.0325,128.048),(42,'민간단체','마장지텃밭',11,'경상북도',167,'포항시 북구','경상북도 북구 창포동 445-1','',36.0658,129.363),(43,'민간단체','옐로우시티',12,'전라남도',197,'장성군','전라남도 장성군 장성읍 1455','',35.3031,126.77),(44,'지자체/주말농장','친환경주말농장',12,'전라남도',195,'영암군','전라남도 영암군 삼호읍 산호 1554','',34.7818,126.466),(45,'민간단체','공원텃밭(4지구)',12,'전라남도',182,'나주시','전라남도 나주시 빛가람동 22-1','교육장, 관정',35.0288,126.784),(46,'민간단체','공원텃밭(3지구)',12,'전라남도',182,'나주시','전라남도 나주시 빛가람동 831','교육장, 관정',35.0091,126.793),(47,'민간단체','제 2기 도시농부회',10,'경상남도',142,'양산시','경상남도 양산시 물금읍 물금리 739-1','',35.3105,128.984),(48,'지자체/시범사업','도심속저탄소녹색 체험장(텃밭) 시범 2',10,'경상남도',142,'양산시','경상남도 양산시 원동면 화제리 910-2','용수시설, 창고',35.3633,128.973),(49,'민간단체','도심속저탄소녹색 체험장(텃밭) 시범',10,'경상남도',142,'양산시','경상남도 양산시 명동 735-9','용수시설, 창고',35.4047,129.162),(50,'지자체/자율','주말농장(거제 일운면)',10,'경상남도',137,'거제시','경상남도 거제시 일운면 476','주차장, 화장실',34.8091,128.694),(51,'지자체/자율','주말농장(거제 일운면1)',10,'경상남도',137,'거제시','경상남도 거제시 일운면 475','주차장, 화장실',34.809,128.694),(52,'지자체/개인','수서주말농장',1,'서울특별시',1,'강남구','서울특별시 강남구 수서동 368-1','',37.49,127.093),(53,'개인','남경원',9,'경기도',120,'양주시','경기도 양주시 삼숭동 345','',37.8018,127.102),(54,'지자체/주말농장','무명텃밭(의왕시 월암동)',9,'경기도',125,'의왕시','경기도 의왕시 월암동 331-5','주차장, 하우스',37.3106,126.955),(55,'지자체/주말농장','무명텃밭(의왕시 왕곡동)',9,'경기도',125,'의왕시','경기도 의왕시 왕곡동 84-1','주차장, 하우스',37.347,126.995),(56,'지자체/주말농장','무명텃밭(의왕시 청계동)',9,'경기도',126,'의정부시','경기도 의왕시 청계동 74-1, 2','하우스',37.4055,127.026),(57,'개인','어여비주말농장',9,'경기도',125,'의왕시','경기도 의왕시 이동 551-3','화장실, 주차장, 바베큐장',37.3316,126.965),(58,'개인','하이디농장',9,'경기도',125,'의왕시','경기도 의왕시 청계동 207-1','화장실, 쉼터, 원두막 등',37.3959,127.024),(59,'개인','송암농장',9,'경기도',125,'의왕시','경기도 의왕시 오전동 22-2','원두막, 쉼터화장실',37.3668,126.965),(60,'개인','청목농장',9,'경기도',125,'의왕시','경기도 의왕시 내손동 547-2','화장실, 원두막',37.3788,126.993),(61,'개인','오매기농장',9,'경기도',125,'의왕시','경기도 의왕시 오전동 423-2','원두막, 쉼터, 화장실',37.3522,126.984),(62,'개인','샘터주말농장',9,'경기도',131,'하남시','경기도 하남시 창우동 271-1','농기구보관창고',37.5358,127.23),(63,'개인','사기막골제1',9,'경기도',96,'과천시','경기도 과천시 문원동 378','',37.4203,126.997),(64,'개인','사기막골제2',9,'경기도',96,'과천시','경기도 과천시 문원동 374-6','',37.4192,126.998),(65,'민간단체','친환경서송옥상텃밭',5,'광주광역시',60,'광산구','광주광역시 광산구 월곡동 52-12','수도시설',35.1684,126.81),(66,'민간단체','행복나눔 공영도시텃밭',5,'광주광역시',60,'광산구','광주광역시 광산구 하남동 641','수도시설, 화장실, 쉼터',35.1763,126.797),(68,'개인','청송리웰촌남새밭',17,'세종특별자치시',249,'세종특별자치시','세종특별자치시 전동면 청라1길 6-6','관수시설, 화장실, 휴게시설',36.5882,127.197),(69,'개인','복사꽃축제텃밭',17,'세종특별자치시',249,'세종특별자치시','세종특별자치시 조치원읍 신흥리 358','관수시설',36.5979,127.284),(70,'개인','예목원주말농장',17,'세종특별자치시',249,'세종특별자치시','세종특별자치시 장군면 은용리 484-2','관수, 휴게시설, 화장실, 농기구창고',36.4832,127.196),(71,'개인','무명텃밭(세종시 금남면)',17,'세종특별자치시',249,'세종특별자치시','세종특별자치시 금남면 영대리 207','관수시설',36.4674,127.337),(72,'개인','무명텃밭(세종시 조치원읍)',17,'세종특별자치시',249,'세종특별자치시','세종특별자치시 조치원읍 남리 56-1','관수시설',36.5969,127.299),(73,'개인','무명텃밭(세종시 조치원읍)',17,'세종특별자치시',249,'세종특별자치시','세종특별자치시 조치원읍 남리 87-2','관수시설',36.5942,127.303),(74,'개인','옥련주말농장',9,'경기도',104,'부천시 소사구','경기도 부천시 소사구 옥길동 479-5','관수시설',37.4715,126.827),(75,'개인','영재네주말농장',9,'경기도',104,'부천시 소사구','경기도 부천시 오정구 대장동 235-7','관수시설',37.5404,126.784),(76,'개인','지연네주말농장',9,'경기도',105,'부천시 오정구','경기도 부천시 오정구 대장동 304-3','관수시설',37.5402,126.788),(77,'개인','OK주말농장',9,'경기도',105,'부천시 오정구','경기도 부천시 오정구 고강동 200-9','관수시설',37.5313,126.813),(78,'개인','도시농업공동체 호미',9,'경기도',105,'부천시 오정구','경기도 부천시 춘의동 307','관수시설',37.5019,126.811),(79,'지자체/주말농장','월문리 텃밭',9,'경기도',102,'남양주시','경기도 남양주시 와부읍 월문리 371-3','',37.6084,127.263),(80,'지자체/주말농장','내곡리 텃밭',9,'경기도',102,'남양주시','경기도 남양주시 진접읍 내곡리 33-3','',37.6792,127.165),(81,'지자체/주말농장','미루나무 텃밭',9,'경기도',102,'남양주시','경기도 남양주시 진접읍 내각리 337-10','화장실',37.7023,127.168),(82,'지자체/주말농장','먹골저수시 텃밭',9,'경기도',102,'남양주시','경기도 남양주시 진건읍 진관리 41','화장실',37.6495,127.176),(83,'지자체/주말농장','가운동텃밭(도농동)',9,'경기도',102,'남양주시','경기도 남양주시 이패동 589-1','화장실',37.6026,127.187),(84,'지자체/주말농장','가운동텃밭(지금동)',9,'경기도',102,'남양주시','경기도 남양주시 삼패동 527-1','화장실',37.6024,127.187),(85,'지자체/주말농장','장안뜰주말농장',9,'경기도',132,'화성시','경기도 화성시 봉담읍 내리 44','주차장, 화장실',37.2387,126.936),(86,'지자체/주말농장','왕림주말농원',9,'경기도',132,'화성시','경기도 화성시 봉담읍 왕림리 30','주차장, 화장실',37.2002,126.943),(87,'개인','청궁주말농장',9,'경기도',132,'화성시','경기도 화성시 봉담읍 내리 816','주차장, 화장실',37.2368,126.924),(88,'개인','새말햇살농장',9,'경기도',132,'화성시','경기도 화성시 봉담읍 동화리 291-1','주차장, 화장실',37.2227,126.959),(89,'개인','기산대우주말농장',9,'경기도',132,'화성시','경기도 화성시 기산동 414-12','주차장, 화장실',37.2271,127.042),(90,'개인','정원농장',9,'경기도',132,'화성시','경기도 화성시 정남면 서봉로 869번길 49','주차장, 화장실',37.1548,126.962),(91,'민간단체','웃다리문화촌',9,'경기도',129,'평택시','경기 평택시 서탄면 용소금각로 438-14 (금각리)','농막, 화장실',37.0687,127.008),(92,'민간단체','바람새마을',9,'경기도',129,'평택시','경기도 평택시 고덕면 궁리 496','수도시설, 화장실',37.0168,127.021),(93,'개인','행복가꿈농장',9,'경기도',129,'평택시','경기도 평택시 청북면 토진리 172-4','농막, 수도시설, 화장실',37.0402,126.95),(94,'민간단체','글갱이주말농장',9,'경기도',129,'평택시','경기도 평택시 현덕면 도대리 348-2','수도시설',36.9666,126.903),(95,'민간단체','행복마을주말농장',9,'경기도',129,'평택시','경기도 평택시 청북면 고잔리 924','수도시설, 농막, 화장실',37.0464,126.894),(96,'개인','정암주말농장',9,'경기도',129,'평택시','경기도 평택시 이충동 576','농막, 화장실, 수도시설',37.0551,127.067),(97,'개인','딸기이야기',9,'경기도',129,'평택시','경기도 평택시 장당길 26-20','농막, 화장실, 수도시설',37.0502,127.063),(98,'개인','청옥주말농장',9,'경기도',129,'평택시','경기도 평택시 청북면 옥길리 산 54-1','농막, 수도시설, 화장실',37.025,126.911),(99,'개인','시흥텃밭지기',9,'경기도',114,'시흥시','경기도 시흥시 군자로 210-39','쉼터',37.362,126.766),(100,'민간단체','착한농부',9,'경기도',114,'시흥시','경기도 시흥시 조남동 594-1','쉼터',37.3724,126.873),(101,'민간단체','한어울림 도시텃밭 공동체',9,'경기도',114,'시흥시','경기도 시흥시 방산동 산37-10','',37.4166,126.771),(102,'민간단체','좋은이웃 공공형 주말농장',9,'경기도',114,'시흥시','경기도 시흥시 정왕동 871-27','쉼터',37.3613,126.743),(103,'민간단체','아파트 주말농장 공동체',9,'경기도',114,'시흥시','경기도 시흥시 정왕동 3-3번지','쉼터',37.3701,126.758),(104,'민간단체','정왕동 주민공동체(환영 주말농장)',9,'경기도',114,'시흥시','경기도 시흥시 정왕동 622번지','쉼터',37.355,126.753),(105,'민간단체','월곶동 주민자취위원회(바다향기 주말농장)',9,'경기도',114,'시흥시','경기도 시흥시 월곶동 995','쉼터',37.3888,126.742),(106,'민간단체','자생화 사랑하는 사람들',9,'경기도',114,'시흥시','경기도 시흥시 군자동 35-1, 2','쉼터',37.3572,126.806),(107,'민간단체','신현동 새마을 협의회',9,'경기도',114,'시흥시','경기도 시흥시 포동 14, 14-3, 14-5번지','쉼터',37.4057,126.784),(108,'민간단체','군자봉 사람들',9,'경기도',114,'시흥시','경기도 시흥시 군자동 313,313-2번지','쉼터',37.3595,126.793),(109,'민간단체','군자진흥원 도시텃밭 공동체',9,'경기도',114,'시흥시','경기도 시흥시 거모동 1282-5,7번지','쉼터',37.3423,126.777),(110,'민간단체','신천동 도시농업추진단',9,'경기도',114,'시흥시','경기도 시흥시 신천동 262','쉼터',37.4343,126.782),(111,'민간단체','여러분농장',9,'경기도',114,'시흥시','경기도 시흥시 월곶동 425-4번지','쉼터',37.3765,126.761),(112,'민간단체','한사람나눔봉사단',9,'경기도',114,'시흥시','경기도 시흥시 정왕동 107','쉼터',37.3722,126.747),(113,'민간단체','시민공동체 텃밭(함줄도시농업공원)',9,'경기도',114,'시흥시','경기도 시흥시 정왕동 1774-1','쉼터',37.3686,126.736),(114,'지자체/체험교육농장, 주말농장','옥길텃밭',9,'경기도',97,'광명시','경기도 광명시 옥길동 70-6','주차장, 화장실, 농업급수, 쉼터',37.4729,126.84),(115,'지자체/주말농장','어르신 주말농장',9,'경기도',127,'이천시','경기도 이천시 관고동 77-11','',37.2846,127.434),(116,'지자체/주말농장','시민행복농장',9,'경기도',120,'양주시','경기도 양주시 은현면 682','화장실, 주차장, 휴게실, 농기구보관함',37.841,126.999),(118,'지자체/주말농장','약수터',9,'경기도',96,'과천시','경기도 과천시 문원동 373-1','',37.4187,126.999),(119,'개인','매봉',9,'경기도',96,'과천시','경기도 과천시 문원동 366','',37.4172,126.999),(120,'개인','자현',9,'경기도',96,'과천시','경기도 과천시 문원동 219','',37.4244,127),(121,'개인','우리',9,'경기도',96,'과천시','경기도 과천시 막계동 323-1','',37.4352,127.005),(122,'개인','즐거운',9,'경기도',96,'과천시','경기도 과천시 막계동 323-5','',37.4345,127.005),(123,'개인','옥탑골',9,'경기도',96,'과천시','경기도 과천시 갈현동 251','',37.4073,126.982),(124,'개인','고씨',9,'경기도',96,'과천시','경기도 과천시 갈현동 300-4','',37.4047,126.98),(125,'민간단체','강언덕마을 고구마 체험장',8,'강원도',80,'춘천시','강원도 춘천시 남면 한덕리 607 외 1필지','주차장, 식수대, 화장실',37.6802,127.613),(126,'민간단체','무명텃밭(동해시 대구동)',8,'강원도',76,'동해시','강원도 동해시 대구동 129','화장실, 급수시설',37.4674,129.139),(127,'민간단체','무명텃밭(동해시 지흥동)',8,'강원도',76,'동해시','강원도 동해시 지흥동 211-1, 211-2, 198','공원, 화장실',37.5036,129.099),(128,'개인','운동텃밭',16,'충청북도',237,'청주시 상당구','충청북도 청주시 상당구 운동동 301, 305-1, 305-3','하우스, 주차장, 이동식화장실',36.602,127.517),(129,'민간단체','정안 농촌체험 휴양마을',16,'충청북도',246,'증평군','충청북도 증평군 증평읍 초중리','주차장, 식수대, 화장실',36.7734,127.577),(130,'민간단체','증안골정보화마을',16,'충청북도',246,'증평군','충청북도 증평군 증평읍 송산리','주차장, 식수대, 화장실',36.8037,127.58),(131,'지자체/주말농장','새롱초롱 체험마을',16,'충청북도',247,'진천군','충청북도 진천군 진천읍 629','주차장, 화장실',36.8302,127.408),(132,'개인','만수농장',13,'전라북도',207,'전주시 덕진구','전라북도 전주시 덕진구 호성동 859','화장실',35.8778,127.139),(133,'개인','학전농장',13,'전라북도',208,'전주시 완산구','전라북도 전주시 완산구 원당동 574','주차장, 화장실, 쉼터',35.7649,127.108),(134,'개인','부평농장',13,'전라북도',208,'전주시 완산구','전라북도 전주시 완산구 평화동3가 259-8','화장실, 쉼터',35.7788,127.118),(135,'개인','장교농장',13,'전라북도',208,'전주시 완산구','전라북도 전주시 완산구 평화동4가 245-9','화장실, 쉼터',35.7649,127.143),(136,'지자체/주말농장','이서마을텃밭',13,'전라북도',214,'완주군','전라북도 완주군 이서면 상개리 570-5','급수시설, 원두막',35.8229,127.026),(137,'민간단체','삼향골',12,'전라남도',183,'목포시','전라남도 목포시 대양동 198','',34.8359,126.426),(138,'지자체/분양','신대도시농업공원',12,'전라남도',184,'순천시','전라남도 순천시 해룡면 신대리 2137','주차장, 교육장, 관정, 화장실, 관리실, 농기구보관실, 퇴비사, 쉼터 등',34.9277,127.555),(139,'지자체/체험 및 교육','신대도시농업공원2',12,'전라남도',184,'순천시','전라남도 순천시 해룡면 신대리 2138','교육, 체험텃밭',34.9259,127.554),(140,'지자체/분양','조례텃밭정원',12,'전라남도',184,'순천시','전라남도 순천시 조례동 382-5','관정, 농기구 보관실, 휀스, 쉼터 등',34.9572,127.528),(141,'지자체/분양','연향텃밭정원',12,'전라남도',184,'순천시','전라남도 순천시 연향동 1621-1','관정, 빗물이용시설, 생태화장실, 퇴비사, 농기구 보관실, 휀스, 쉼터 등',34.9393,127.523),(142,'지자체/주말농장','도시민 친환경 가족텃밭',12,'전라남도',185,'여수시','전라남도 여수시 여천동 896-2, 896-4','',34.7807,127.664),(143,'개인','나무향기 현천골농장',12,'전라남도',185,'여수시','전라남도 여수시 소라면 현천리 673-7','평상, 창고',34.7591,127.608),(144,'개인','힐링캠프',12,'전라남도',185,'여수시','전라남도 여수시 선원동 848-3, 849-1','평상, 창고',34.7704,127.656),(145,'지자체/도시농업공원텃밭','공원텃밭(2지구)',12,'전라남도',182,'나주시','전라남도 나주시 빛가람동 864','관리사, 교육장, 화장실, 관정',35.0097,126.799),(146,'지자체/개인','플라워농장',1,'서울특별시',2,'강동구','서울특별시 강동구 암사동 381','화장실, 쉼터',37.5577,127.138),(147,'지자체/주말농장','양지마을 텃밭',1,'서울특별시',2,'강동구','서울특별시 강동구 암사동 199-2','주차장, 화장실, 야외테이블 등',37.558,127.133),(148,'지자체/주말농장','암사역사생태공원텃밭',1,'서울특별시',2,'강동구','서울특별시 강동구 암사동 253-3','화장실, 야외테이블 등',37.5575,127.134),(149,'지자체/주말농장','무명(광진구 광장동)',1,'서울특별시',6,'광진구','서울특별시 광진구 광장동 378','주차장, 화장실',37.5494,127.101),(150,'지자체/주말농장','무명(광진구 광장동2)',1,'서울특별시',6,'광진구','서울특별시 광진구 광장동 582-3','주차장, 화장실',37.5445,127.099),(151,'지자체/주말농장','무명(광진구 중곡동)',1,'서울특별시',6,'광진구','서울특별시 광진구 중곡동 582-3','',37.5715,127.081),(152,'개인','단비네',1,'서울특별시',1,'강남구','서울특별시 도봉구 도봉동 431-5','주차장, 식수대, 화장실',37.6798,127.036),(153,'지자체/주말농장','도봉숲속마을',1,'서울특별시',10,'도봉구','서울특별시 도봉구 도봉동 5-2','주차장, 식수대, 화장실',37.6913,127.048),(154,'지자체/주말농장','성동 무지개텃밭',1,'서울특별시',16,'성동구','서울특별시 성동구 행당동 3-76','관리사무실, 창고, 급수시설, 원두막, 주차장, 화장실 등',37.5607,127.04),(155,'지자체/주말농장','송파산골주말농장',1,'서울특별시',18,'송파구','서울특별시 송파구 방이동 436-59','주차장, 식수대, 화장실',37.5139,127.14),(156,'지자체/주말농장','유한농원',1,'서울특별시',19,'양천구','서울특별시 양천구 신월동 160-2','주차장, 식수대, 화장실',37.5352,126.825),(157,'지자체/주말농장','향림도시농업체험원',1,'서울특별시',22,'은평구','서울특별시 은평구 불광동 457','관리사무소,  화장실, 수도 및 주차장',37.6281,126.926),(158,'지자체/주말농장','이화농원1',1,'서울특별시',25,'중랑구','서울특별시 중랑구 신내동 779-4','주차장, 식수대, 화장실',37.6134,127.09),(159,'지자체/주말농장','이화농원2',1,'서울특별시',25,'중랑구','서울특별시 중랑구 신내동 780-1','주차장, 화장실, 식수대',37.6116,127.089),(160,'지자체/주말농장','6무텃밭',9,'경기도',93,'고양시 덕양구','경기도 고양시 대덕동 492-1','농기구 공동사용',37.5999,126.856),(161,'지자체/주말농장','밤나무주말농장',3,'대구광역시',44,'동구','대구광역시 동구 미대동 526-3','',35.9512,128.678),(162,'지자체/주말농장','후계자주말농장',3,'대구광역시',44,'동구','대구광역시 동구 미대동 605','',35.951,128.674),(163,'지자체/주말농장','내동주말농장',3,'대구광역시',44,'동구','대구광역시 동구 내동 466','',35.96,128.663),(164,'지자체/주말농장','천을산농장',3,'대구광역시',47,'수성구','대구광역시 수성구 매호동 195','화장실, 쉼터',35.8512,128.7),(165,'지자체/체험프로그램 운영 및 주말농장','천을산농장2',3,'대구광역시',47,'수성구','대구광역시 수성구 가천동 343','농업용수',35.8519,128.698),(166,'지자체/체험프로그램운영 및 주말농장','천을산농장3',3,'대구광역시',47,'수성구','대구광역시 수성구 매호동 208, 209, 232, 288','농업용수',35.8506,128.702),(167,'지자체/공동경작 및 복지사업운영','가천농장',3,'대구광역시',47,'수성구','대구광역시 수성구 가천동 109-2,3','농업용수',35.8556,128.684),(168,'지자체/주말농장','휴일팜핑농장',3,'대구광역시',49,'달성군','대구광역시 달성군 다사읍 문양리 960','교육장, 체험장, 쉼터 등',35.8664,128.424),(169,'지자체/주말농장','라온농장',3,'대구광역시',49,'달성군','대구광역시 달성군 다사읍 부곡리 703-2','쉼터, 교육장 등',35.8532,128.441),(170,'개인','천향허브주말농장',3,'대구광역시',43,'달서구','대구광역시 달성군 옥포면 기세리 37-2','체험장, 육묘장 등',35.7879,128.475),(171,'개인','대밭골주말농장',3,'대구광역시',42,'남구','대구광역시 달성군 다사읍 문양리 681','쉼터, 화장실 등',35.8675,128.43),(172,'개인','농따라주말농장',3,'대구광역시',49,'달성군','대구광역시 달성군 유가면 도의리 1086-3','쉼터, 화장실 등',35.6569,128.454),(173,'개인','운산주말농장',3,'대구광역시',49,'달성군','대구광역시 달성군 옥포면 본리리 1816-3','쉼터, 화장실 등',35.7909,128.448),(174,'지자체/주말농장','주말농장(금포리)',3,'대구광역시',49,'달성군','대구광역시 달성군 논공읍 금포리 340','쉼터, 화장실 등',35.7892,128.42),(175,'개인','두메산채원주말농장',3,'대구광역시',49,'달성군','대구광역시 달성군 옥포면 반송리 54','쉼터, 화장실 등',35.7561,128.503),(176,'지자체/주말농장','여울채주말농장',3,'대구광역시',49,'달성군','대구광역시 달성군 다사읍 죽곡리 762','체험장, 화장실 등',35.8422,128.466),(177,'지자체/주민참여운영','주민참여텃밭(주안동)',4,'인천광역시',51,'남구','인천광역시 남구 주안동 854-45','',37.4469,126.675),(178,'지자체/주민자율운영','주민참여텃밭(숭의동)',4,'인천광역시',51,'남구','인천광역시 남구 숭의동 224-3','',37.4631,126.652),(179,'지자체/주민참여텃밭','주민참여텃밭(용현동)',1,'서울특별시',1,'강남구','인천광역시 남구 용현동 454-134','',37.4574,126.656),(180,'지자체/주민참여텃밭','주민참여텃밭(용현동2)',4,'인천광역시',51,'남구','인천광역시 남구 용현동 147-42','',37.4545,126.655),(181,'지자체/주민자율운영','주민참여텃밭(숭의동2)',4,'인천광역시',51,'남구','인천광역시 남구 숭의동 162-40','',37.4634,126.645),(182,'지자체/주민자율운영','주민참여텃밭(숭의동3)',4,'인천광역시',51,'남구','인천광역시 남구 숭의동 10-28','',37.4588,126.66),(183,'지자체/주말농장','사랑텃밭',4,'인천광역시',56,'연수구','인천광역시 연수구 연수동 581-2','창고, 화장실',37.4187,126.691),(184,'지자체/주말농장','행복텃밭',4,'인천광역시',56,'연수구','인천광역시 연수구 송도동 107-1','창고, 화장실',37.3833,126.635),(185,'지자체/주말농장','실버농장',4,'인천광역시',56,'연수구','인천광역시 연수구 선학동 149-1','창고, 화장실',37.4316,126.698),(186,'지자체/주말농장','남인천농협 주말농장',4,'인천광역시',52,'남동구','인천광역시 남동구 남촌동 102','급수, 휴게시설, 화장실',37.4291,126.719),(187,'개인','무명(서창동 농장)',4,'인천광역시',52,'남동구','인천광역시 남동구 서창동 10-1','',37.4437,126.756),(188,'지자체/공동체 텃밭','도시농부 체험교육농장',4,'인천광역시',54,'부평구','인천광역시 부평구 십정동 97-2','보관창고, 화장실',37.4815,126.691),(189,'지자체/단체 분양','서구도시 농업농장',4,'인천광역시',55,'서구','인천광역시 서구 시천동 114번지 외 6필지','주차장, 쉼터시설',37.5722,126.686),(190,'지자체/주말농장','행복키움주말농장(소태동)',5,'광주광역시',62,'동구','광주광역시 동구 소태동 94','관수시설, 원두막, 농기구함 등',35.12,126.944),(191,'지자체/주말농장','행복키움주말농장(내남동)',5,'광주광역시',62,'동구','광주광역시 동구 내남동 184','관수시설, 휴게쉼터, 농기구함 등',35.0862,126.94),(192,'지자체/주말농장','행복키움주말농장(용연동)',5,'광주광역시',62,'동구','광주광역시 동구 용연동 423-1,426-1','관수시설, 파고라, 농기구함 등',35.0947,126.963),(193,'지자체/주말농장','행복키움주말농장(용산동)',5,'광주광역시',62,'동구','광주광역시 동구 용산동 80-1','관수시설, 휴게쉼터, 농기구함 등',35.1114,126.934),(194,'지자체/공영도시텃밭','양동 친환경공영도시텃밭',5,'광주광역시',64,'서구','광주광역시 서구 양동 406','창고, 휴게실, 관정',35.1567,126.894),(195,'지자체/공영도시텃밭','풍암동 친환경공영도시텃밭',5,'광주광역시',64,'서구','광주광역시 서구 풍암동 383-1','창고, 휴게실, 관정',35.1293,126.876),(196,'민간단체','건강희망텃밭',5,'광주광역시',61,'남구','광주광역시 남구 양과동 957 외 2필지','',35.0874,126.864),(197,'민간단체','무명(광주 월계동)',5,'광주광역시',63,'북구','광주광역시 월계동 830-2','수도시설',35.2122,126.842),(198,'민간단체','반월공동체텃밭',5,'광주광역시',60,'광산구','광주광역시 광산구 월곡동 110-7, 산105-12','',35.1658,126.816),(199,'민간단체','인계마을공동체희망텃밭',5,'광주광역시',60,'광산구','광주광역시 광산구 우산동 1601-7/889-3','',35.1602,126.807),(200,'민간단체','행복한텃밭',5,'광주광역시',60,'광산구','광주광역시 광산구 도산동631,632','',35.1253,126.793),(201,'개인','산울림농장(역곡동)',9,'경기도',106,'부천시 원미구','경기도 부천시 원미구 역곡동 186-3','관수시설',37.4912,126.806),(202,'지자체/도시텃밭','무명텃밭(광주 신촌동)',5,'광주광역시',60,'광산구','광주광역시 광산구 신촌동 1072-8','원두막',35.1467,126.802),(203,'민간단체','무명텃밭(광주 월곡동)',5,'광주광역시',60,'광산구','광주광역시 월곡동 490-7','수도시설',35.1744,126.808),(204,'지자체/개인','새로운 주말농장',4,'인천광역시',52,'남동구','인천시 남촌동 232-1','수도시설',37.4261,126.716),(205,'지자체/개인','도봉예전',1,'서울특별시',10,'도봉구','서울시 도봉구 도봉동 437-5','주차장, 화장실',37.6788,127.036),(206,'지자체/주말농장','동구행복나눔텃밭',3,'대구광역시',44,'동구','대구광역시 동구 불로동 823-12','-',35.8996,128.634),(207,'지자체/주말농장','에덴주말농장',1,'서울특별시',1,'강남구','서울 강남구 개포동 112','주차장, 식수대, 화장실',37.4833,127.073),(208,'지자체/개인','수서주말농장',1,'서울특별시',1,'강남구','서울특별시 강남구 수서동 369','주차장',37.4907,127.092),(209,'지자체/주말농장','문화동산농장',9,'경기도',106,'부천시 원미구','경기도 부천시 원미구 상동 529-2','주차장,화장실,EM저장고',37.5102,126.746),(210,'지자체/안산시청','화랑농장',9,'경기도',115,'안산시 단원구','안산시 단원구 초지동 662-2','주차장, 화장실',37.3237,126.743),(211,'지자체/개인','웰빙주말농장',9,'경기도',116,'안산시 상록구','안산시 양상동 280번지','',37.3431,126.855),(212,'지자체/개인','블루팜',9,'경기도',102,'남양주시','남양주시 와부읍 율석리 683-2','',37.6065,127.215),(213,'지자체/개인','도심주말농장',9,'경기도',102,'남양주시','남양주시 와부읍 도곡리 1034-2','',37.5802,127.224),(214,'지자체/개인','석실주말농장',9,'경기도',102,'남양주시','남양주시 와부읍 덕소리 311-3','',37.6006,127.217),(215,'지자체/개인','광릉숲주말농장',9,'경기도',102,'남양주시','남양주시 진접읍 부평리 266','',37.7505,127.193),(216,'지자체/개인','송능주말농장',9,'경기도',102,'남양주시','남양주시 진건읍 송능리 386-18','',37.6473,127.2),(217,'지자체/개인','곤충나라 스머프농장',9,'경기도',102,'남양주시','남양주시 진건읍 신월리 113-1','',37.6612,127.167),(218,'지자체/개인','재희농장',9,'경기도',102,'남양주시','남양주시 오남읍 오남리 699번지','',37.6925,127.208),(219,'지자체/개인','아림농장',9,'경기도',102,'남양주시','남양주시 퇴계원면 퇴계원리 357','',37.6451,127.133),(220,'지자체/개인','성지농원',9,'경기도',102,'남양주시','남양주시 수동면 지둔리 335번지','',37.7176,127.3),(221,'지자체/개인','산삼농장',9,'경기도',102,'남양주시','남양주시 수동면 수산리 165-1','',37.7245,127.279),(222,'지자체/개인','주말농장',9,'경기도',102,'남양주시','남양주시 호평로 69번길 16','',37.6574,127.249),(223,'지자체/개인','운산주말농장',9,'경기도',102,'남양주시','남양주시 별내면 청학리 23번지','',37.7132,127.127),(224,'지자체/개인','화랑농원',9,'경기도',102,'남양주시','남양주시 별내면 용암리 326-3','',37.7228,127.135),(225,'지자체/개인','수궁농원',9,'경기도',102,'남양주시','남양주시 덕송2리 산 2-79','',37.6608,127.14),(226,'지자체/개인','양정주말농장',9,'경기도',102,'남양주시','남양주시 일패동 703-3번지','',37.6195,127.177),(227,'지자체/개인','우리주말농장',9,'경기도',102,'남양주시','남양주시 일패동 755-2번지','',37.6155,127.18),(228,'지자체/개인','행복한주말농장',9,'경기도',120,'양주시','양주시 고읍동','',37.8052,127.075),(229,'지자체/개인','동현이농장',9,'경기도',126,'의정부시','의정부시 자일동 302번지','수도, 원두막, 표찰, 주차장',37.7573,127.086),(230,'지자체/개인','담다헌농장',9,'경기도',126,'의정부시','의정부시 산곡동','수도, 원두막, 천막, 화장실, 표찰',37.7175,127.105),(231,'지자체/개인','숲속시현농장',9,'경기도',126,'의정부시','의정부시 자일동 178번지','수도, 원두막, 천막, 화장실, 샤워장',37.765,127.098),(232,'지자체/탄현면','살래텃밭',9,'경기도',128,'파주시','파주시 탄현면 성동리 706번지','',37.7829,126.692),(233,'지자체/민간','선사주말농장',1,'서울특별시',2,'강동구','강동구 암사동 189','주차장, 식수대, 화장실',37.558,127.13),(234,'지자체/민간','둔촌텃밭농원',1,'서울특별시',2,'강동구','강동구 둔촌동 125-1','주차장, 식수대, 화장실',37.5248,127.145),(235,'지자체/민간','토끼굴텃밭농원',1,'서울특별시',2,'강동구','강동구 암사동 603-5','주차장, 식수대, 화장실',37.5544,127.126),(236,'지자체/민간','동양사주말농장',1,'서울특별시',2,'강동구','강동구 상일동469-3','',37.5454,127.163),(237,'지자체/민간','암사동농장',1,'서울특별시',2,'강동구','강동구 암사동207-4','',37.5569,127.133),(238,'지자체/민간','암사주말농장',1,'서울특별시',2,'강동구','강동구 암사동 338','주차장, 식수대, 화장실',37.5591,127.141),(239,'지자체/민간','고덕주말농장',1,'서울특별시',2,'강동구','강동구 고덕동 334','주차장, 식수대, 화장실',37.5641,127.161),(240,'지자체/민간','암사가족농원',1,'서울특별시',2,'강동구','강동구 암사동 380-9','주차장, 식수대, 화장실',37.5579,127.138),(241,'지자체/민간','고덕주말농장2',1,'서울특별시',2,'강동구','강동구 고덕1동 479','주차장, 식수대, 화장실',37.5635,127.156),(242,'지자체/민간','과해주말농장',1,'서울특별시',4,'강서구','강서구 오곡동 518-2','쉼터, 주차장, 세면장, 화장실 등',37.5561,126.778),(243,'지자체/민간','신정텃밭농장',1,'서울특별시',19,'양천구','양천구 신월7동 728-10','주차장, 식수대, 화장실',37.5101,126.826),(244,'지자체/민간','삼각산주말농장',1,'서울특별시',3,'강북구','강북구 강북구 4.19로 12길 76','주차장, 식수대, 화장실',37.6458,127.003),(245,'지자체/민간','삼각산주말농장2',1,'서울특별시',3,'강북구','강북구 우이동 73-134','주차장, 식수대, 화장실',37.6514,127.011),(246,'지자체/민간','천수텃밭농원',1,'서울특별시',9,'노원구','노원구 중계본동 산114','주차장, 식수대, 화장실',37.6471,127.086),(247,'지자체/민간','무수골주말농장',1,'서울특별시',10,'도봉구','도봉구 도봉1동 469','주차장, 식수대, 화장실',37.6775,127.033),(248,'지자체/민간','도봉산주말농장',1,'서울특별시',10,'도봉구','도봉구 도봉1동 384-12','',37.6897,127.04),(250,'지자체/민간','도봉산초원주말농장',1,'서울특별시',10,'도봉구','도봉구 도봉구 평화로 15번길 9-23','',37.6936,127.043),(251,'지자체/민간','정의공주',1,'서울특별시',10,'도봉구','도봉구 방학동 571','주차장, 화장실',37.6628,127.023),(252,'지자체/민간','웰빙주말농장',1,'서울특별시',10,'도봉구','도봉구 도봉동 468','',37.6769,127.033),(253,'지자체/민간','샘골주말농장',1,'서울특별시',10,'도봉구','도봉구 방학동 534','',37.6594,127.021),(254,'지자체/민간','대원',1,'서울특별시',15,'서초구','서초구 원지동 227','',37.4515,127.048),(255,'지자체/민간','옛골텃밭농원',1,'서울특별시',15,'서초구','서초구 신원동 44-2','주차장, 식수대, 화장실',37.4347,127.066),(256,'지자체/민간','청계주말농장',1,'서울특별시',15,'서초구','서초구 원지동 530','주차장, 식수대, 화장실',37.4353,127.063),(257,'지자체/민간','들꽃풍경',1,'서울특별시',15,'서초구','서초구 내곡동 1-342','주차장, 식수대, 화장실',37.4541,127.079),(258,'지자체/민간','칠성주말농장',1,'서울특별시',15,'서초구','서초구 원지동 370','주차장, 식수대, 화장실',37.444,127.055),(259,'지자체/민간','우리농원',1,'서울특별시',18,'송파구','송파구 방이동 436-18','주차장, 식수대, 화장실',37.5141,127.141),(260,'지자체/민간','대훈농원(배)',1,'서울특별시',25,'중랑구','중랑구 신내동 256-2','주차장, 화장실, 식수대',37.6202,127.108),(261,'지자체/민간','신내농장',1,'서울특별시',25,'중랑구','중랑구 중랑구 신내동 256-10','주차장, 식수대, 화장실',37.6191,127.109),(262,'지자체/민간','용마산주말농장',1,'서울특별시',25,'중랑구','중랑구 용마산로 70길 81','주차장, 식수대, 화장실',37.582,127.099),(263,'지자체/민간','먹골농원',1,'서울특별시',25,'중랑구','중랑구 신내동 779-4','',37.6135,127.09),(264,'지자체/민간','대산농원',1,'서울특별시',25,'중랑구','중랑구 신내동 774-2','주차장, 식수대, 화장실',37.613,127.087),(265,'지자체/민간','궁동주말농장',1,'서울특별시',7,'구로구','구로구 궁동 1-2','주차장, 식수대, 화장실',37.5074,126.828),(266,'지자체/민간','삼거리주말농장',1,'서울특별시',7,'구로구','구로구 궁동 8외 3필지','',37.5068,126.828),(267,'지자체/민간','북한산주말농장',1,'서울특별시',22,'은평구','은평구 진관동 272-3','주차장, 식수대, 화장실',37.6545,126.947),(268,'지자체/강동구청','가래여울텃밭',1,'서울특별시',2,'강동구','강동구 강일동 138-17외 2','주차장, 화장실, 야외테이블 등',37.5773,127.17),(269,'지자체/강동구청','강일텃밭',1,'서울특별시',2,'강동구','강동구 강일동 33-3 외 7','주차장, 화장실, 야외테이블 등',37.5784,127.175),(270,'지자체/강동구청','둔촌텃밭',1,'서울특별시',2,'강동구','강동구 둔촌동 118-1','주차장, 화장실, 야외테이블 등',37.5236,127.146),(271,'지자체/강동구청','상일텃밭',1,'서울특별시',2,'강동구','강동구 상일동 12 외 1','주차장, 화장실, 야외테이블 등',37.5481,127.182),(272,'지자체/강동구청','암사텃밭',1,'서울특별시',2,'강동구','강동구 암사동 603-3','주차장, 화장실, 야외테이블 등',37.555,127.126),(273,'지자체/개인','남촌동 주말농장',4,'인천광역시',52,'남동구','인천시 남촌동 241-3','화장실',37.4274,126.717),(274,'지자체/개인','고씨네 주말농장',4,'인천광역시',52,'남동구','인천시 서창동 산 30-1','수도시설, 휴게시설, 화장실',37.4411,126.753),(275,'지자체/개인','별내주말농장',9,'경기도',102,'남양주시','남양주시 별내동 2261','',37.6855,127.109),(276,'지자체/개인','곤재농장',9,'경기도',126,'의정부시','의정부시 낙양동','수도, 화장실',37.7568,127.114),(277,'지자체/인천교구청','만수6동성당 주말농장',4,'인천광역시',52,'남동구','인천시 도림동 5','수도시설, 휴게시설, 화장실',37.4273,126.732),(278,'지자체/도봉구청','쌍문동친환경 나눔텃밭',1,'서울특별시',10,'도봉구','서울시 도봉구 쌍문동 442-1','화장실, 쉼터',37.6514,127.019),(280,'지자체/시흥시','은행동주말농장',9,'경기도',114,'시흥시','시흥시 안현동 99','쉼터',37.4337,126.814),(281,'지자체/시흥시','은행동 새마을남녀지도자회',9,'경기도',114,'시흥시','시흥시 안현동 117-1','쉼터',37.4326,126.811),(282,'지자체/공영주말농장','강남친환경도시텃밭',1,'서울특별시',1,'강남구','서울특별시 강남구 수서동 370','휴게실, 급수 등',37.4912,127.092),(283,'지자체/공영주말농장','호미질주말농장',1,'서울특별시',1,'강남구','인천광역시 계양구 다남동 103-33','화장실, 식수대',37.566,126.726),(285,'지자체/민영주말농장','여울농장',1,'서울특별시',14,'서대문구','경기도 양주시 장흥면 삼상리  446-12','주차장, 화장실 등',37.6882,126.93),(286,'지자체/민영주말농장','남태령주말농장',1,'서울특별시',15,'서초구','서울특별시 서초구 방배동 산 139','화장실, 주차장',37.4678,126.994),(287,'지자체/공영주말농장','무지개텃밭',1,'서울특별시',16,'성동구','서울특별시 성동구 행당동 76-3','관리사무시, 창고, 급수(수도)시설,원두막,주차장,화장실 등',37.5541,127.041),(288,'지자체/주말농장','무척산 관광예술원',10,'경상남도',138,'김해시','경상남도 김해시 생림면 안양리817','쉼터, 화장실, 관수시설, 주차장',35.3805,128.841),(289,'지자체/주말농장','생림면주말농장',10,'경상남도',138,'김해시','경상남도 김해시 생림면 마사리1556','쉼터, 화장실, 관수시설, 주차장',35.372,128.824),(290,'지자체/주말농장','생태체험학교 참빛',10,'경상남도',138,'김해시','경상남도 김해시 한림면 가산리 191-2','쉼터, 화장실, 관수시설, 주차장',35.3317,128.76),(291,'지자체/주말농장','의숙텃밭',10,'경상남도',138,'김해시','경상남도 김해시 생림면 마사리1640','쉼터, 화장실, 관수시설',35.3612,128.809),(292,'지자체/주말농장','태평텃밭',10,'경상남도',138,'김해시','경상남도 김해시 진영읍 우동리186-1','쉼터, 화장실, 관수시설',35.2751,128.722),(293,'지자체/개인','증산정자나무',10,'경상남도',142,'양산시','경상남도 양산시 물금읍 증산리 104-5','주차장',35.3032,129.003),(294,'지자체/주말농장','초록마을도시농부주말농장',10,'경상남도',143,'진주시','경상남도 진주시 금산면 중천리 484','주차장',35.2107,128.143),(295,'지자체/주말농장','통영시주말농장',10,'경상남도',146,'통영시','경상남도 통영시 광도면 죽림리 376-1','휴게시설',34.8769,128.412),(296,'지자체/주말농장','농촌사랑도시텃밭',11,'경상북도',164,'영주시','경상북도 영주시 아지동 233-1','화장실,세면장,쉼터3',36.8511,128.592),(297,'지자체/주말농장','행복텃밭',11,'경상북도',167,'포항시 북구','경상북도 포항시 북구 두호동 산30-1','주차장,식수대',36.0705,129.384),(298,'지자체/주말농장','예천온천주말농장',11,'경상북도',174,'예천군','경상북도 예천군 감천 119','화장실,세면장,원두막,주차장',36.7236,128.5),(299,'지자체/주말농장','농사체험학습장',12,'전라남도',181,'광양시','전라남도 광양시 봉강면 지곡리 862-19외','주차장, 급수시설, 화장실 등',34.9898,127.577),(300,'지자체/주말농장','허브체험관광농원',13,'전라북도',205,'남원시','전라북도 남원시 운봉읍 294-1','체험장, 식물원 등',35.4041,127.519),(301,'지자체/주말농장','낙정마을텃밭',13,'전라북도',214,'완주군','전라북도 완주군 봉동읍 낙평리 303','급수시설, 원두막',35.9301,127.163),(302,'지자체/주말농장','두억시민텃밭',13,'전라북도',214,'완주군','전라북도 완주군 용진읍 간중리 14-3','급수시설, 정자, 주차장',35.9035,127.203),(303,'지자체/주말농장','새터마을텃밭',13,'전라북도',214,'완주군','전라북도 완주군 삼례읍 삼례리 194','급수시설, 농기구 보관함, 정자',35.9209,127.082),(304,'지자체/주말농장','서두시민텃밭',13,'전라북도',214,'완주군','전라북도 완주군 봉동읍 구미리 12-12','급수시설, 정자, 주차장',35.9419,127.149),(305,'지자체/농가직접운영','산성텃밭',16,'충청북도',237,'청주시 상당구','충청북도 청주시 상당구 산성동 35','하우스,주차장,이동식화장실',36.6591,127.544),(306,'지자체/농가직접운영','용정텃밭',16,'충청북도',237,'청주시 상당구','충청북도 청주시 상당구 용정동 413-2','하우스,주차장,이동식화장실',36.631,127.521),(307,'지자체/농가직접운영','강내텃밭',16,'충청북도',238,'청주시 흥덕구','충청북도 청주시 흥덕구 강내면 궁현리 산56-1','하우스,주차장,이동식화장실',36.5751,127.366),(308,'지자체/민간위탁','오송텃밭',16,'충청북도',238,'청주시 흥덕구','충청북도 청주시 흥덕구 오송읍 만수리 286-1','하우스,주차장,이동식화장실',36.6319,127.336),(309,'지자체/농가직접운영','옥산텃밭',16,'충청북도',238,'청주시 흥덕구','충청북도 청주시 흥덕구 옥산면 소로리 187-2','하우스,주차장,이동식화장실',36.6914,127.408),(310,'지자체/임대','도시민 녹색체험 텃밭',16,'충청북도',239,'충주시','충청북도 충주시 동량면 대전리 1665','화장실,농기구창고,주차장',37.0446,127.929),(311,'지자체/농가직접운영','오창텃밭',16,'충청북도',248,'청주시 청원구','충청북도  오창읍 탑리 202-2','하우스,주차장,이동식화장실',36.6971,127.453),(312,'지자체/주말농장','명지공영시민텃밭',2,'부산광역시',26,'강서구','부산광역시 강서구 명지동 3242-2','주차장,화장실 등',35.0803,128.908),(313,'지자체/주말농장','동부산권공영시민텃밭',2,'부산광역시',41,'기장군','부산광역시 기장군 철마면 웅천리 311','주차장,화장실 등',35.2925,129.169),(314,'지자체/주말농장','분토농업주말농장',5,'광주광역시',63,'북구','광주광역시 북구 청풍동 1311-3','수도, 쉼터',35.187,126.957),(315,'지자체/주말농장','우리두리주말농장',5,'광주광역시',63,'북구','광주광역시 북구 청풍동 420-1','쉼터,수도,동물농장 등',35.1721,126.96),(316,'지자체/주말농장','낙원농장',9,'경기도',120,'양주시','경기도 양주시 은남로 119번길 81-12','화장실,주차장',37.899,127.004),(317,'지자체/주말농장','노고산',9,'경기도',120,'양주시','경기도 양주시 일영로 502번길 108-33','화장실,주차장,휴게실,농기구보관함',37.6896,126.932),(318,'지자체/주말농장','능모루들녁',9,'경기도',120,'양주시','경기도 양주시 일영로 375번길 147','',37.6757,126.916),(319,'지자체/주말농장','매내미농장',9,'경기도',120,'양주시','경기도 양주시 일영로 327번길 16','화장실,주차장,휴게실',37.6751,126.92),(320,'지자체/주말농장','방아다리',9,'경기도',120,'양주시','경기도 양주시 일영로 423-14','화장실,주차장,휴게실',37.6827,126.921),(321,'지자체/주말농장','사랑방농원',9,'경기도',120,'양주시','경기도 양주시 일영로 638','화장실,주차장,휴게실,농기구보관함',37.6994,126.931),(322,'지자체/주말농장','씨알농장',9,'경기도',120,'양주시','경기도 양주시 부흥로 1907번길 49-86','화장실,주차장,휴게실,농기구보관함',37.7961,127.069),(323,'지자체/주말농장','양촌리',9,'경기도',120,'양주시','경기도 양주시 일영로 327번길 227-15','간이화장실, 주차장',37.6725,126.91),(324,'지자체/주말농장','여울농장',9,'경기도',120,'양주시','경기도 양주시 일영로 502번길 52-57','화장실,주차장,휴게실,농기구보관함',37.6882,126.93),(325,'지자체/주말농장','은아네농장',9,'경기도',120,'양주시','경기도 양주시 일영로 740번길 18-65','화장실,주차장,휴게실',37.7078,126.937),(326,'지자체/주말농장','천보웰리스',9,'경기도',120,'양주시','경기도 양주시 원학로  184번길 142-38','화장실,주차장,휴게실,농기구보관함',37.7712,127.07),(327,'지자체/주말농장','한마음주말농장',9,'경기도',120,'양주시','경기도 양주시 고삼로 139','화장실,주차장',37.7976,127.099),(328,'지자체/주말농장','홍정섭농원',9,'경기도',120,'양주시','경기도 양주시 광적로 101','',37.8276,126.98),(329,'지자체/주말농장','동산주말농장',9,'경기도',131,'하남시','경기도 하남시 교산동  123-2','농기구보관함, 관수시설 등',37.5217,127.203),(330,'지자체/주말농장','우리주말농장',9,'경기도',132,'화성시','경기도 화성시 진안동 114','수도,쉼터',37.2218,127.037),(331,'지자체/주말농장','옥길시민주말농장',9,'경기도',97,'광명시','경기도 광명시 옥길동 259-26번지','주차장, 화장실, 테이블, 쉼터 등',37.4611,126.842),(332,'지자체/체험교육농장','새싹농장',8,'강원도',78,'속초시','강원도 속초시 노학동 1073-50','없음',38.2059,128.515),(333,'지자체/주말농장','주말가족농장',8,'강원도',78,'속초시','강원도 속초시 교동 외 2개동 882-2','없음',38.1969,128.572),(334,'지자체/주말농장','끝셈무지개농원',9,'경기도',116,'안산시 상록구','경기도 군포시 부곡동 207-3','주차장,화장실',37.3372,126.933),(335,'지자체/민간','힐링주말농장',1,'서울특별시',10,'도봉구','도봉구 방학동 392','주차장, 식수대, 화장실',37.6713,127.033),(336,'지자체/강동구청','명일근린공원 공동체텃밭',1,'서울특별시',2,'강동구','강동구 상일동145-6','교육장, 씨앗도서관, 주차장, 화장실, 야외테이블 등',37.5549,127.16),(337,'지자체/개인','남영 주말농장',4,'인천광역시',52,'남동구','인천시 남촌동 244-1','수도시설, 휴게시설, 화장실',37.4278,126.717),(338,'지자체/개인','오봉산 주말농장',4,'인천광역시',52,'남동구','인천시 논현동 51-2','수도시설, 휴게시설, 화장실',37.4111,126.734),(339,'지자체/개인','푸르미 주말농장',4,'인천광역시',52,'남동구','인천시 도림동 439','수도시설, 휴게시설, 화장실',37.4144,126.72),(340,'지자체/개인','태현농장',4,'인천광역시',52,'남동구','인천시 만수동 756-1','수도시설, 휴게시설, 화장실',37.4348,126.742),(341,'지자체/개인','만의골 주말농장',4,'인천광역시',52,'남동구','인천시 장수동 117-1','수도시설, 휴게시설, 화장실',37.458,126.771),(342,'지자체/개인','청정 주말농장',4,'인천광역시',52,'남동구','인천시 장수동 154','수도시설, 휴게시설, 화장실',37.4557,126.771),(343,'지자체/개인','알뜰 주말농장',4,'인천광역시',52,'남동구','인천시 장수동 175-3','수도시설, 휴게시설, 화장실',37.454,126.771),(344,'지자체/남동구청','남동구 공공주말농장',4,'인천광역시',52,'남동구','인천시 남촌동 510-8','수도시설, 휴게시설, 화장실',37.4293,126.707),(345,'지자체/남동구청','남동구 실버농장',4,'인천광역시',52,'남동구','인천시 수산동 13-1','수도시설, 휴게시설, 화장실',37.444,126.733),(346,'지자체/인천도시농업네트워크','논고개텃밭',4,'인천광역시',52,'남동구','인천시 도림동 116-1','휴게시설, 화장실',37.4225,126.733),(347,'지자체/남동희망공간','남동희망공간 주말농장',4,'인천광역시',52,'남동구','인천시 도림동 526-1','',37.4268,126.723),(348,'지자체/인천도시농업네트워크','서창텃밭',4,'인천광역시',52,'남동구','인천시 서창동 15-2','수도시설, 휴게시설, 화장실',37.4409,126.755),(349,'지자체/인천사람연대','지렁이 주말농장',4,'인천광역시',52,'남동구','인천시 운연동 4','수도시설, 휴게시설',37.4481,126.765),(350,'지자체/도봉구청','초안산 근린 공원나눔텃밭',1,'서울특별시',10,'도봉구','서울시 도봉구 창동 산 157','화장실, 쉼터',37.6479,127.045),(352,'지자체/개인','도봉산',1,'서울특별시',10,'도봉구','서울시 도봉구 도봉동 384-10','주차장, 화장실',37.6899,127.04),(353,'지자체/개인','도봉산초원',1,'서울특별시',10,'도봉구','서울시 도봉구 도봉동 380','주차장, 화장실',37.6936,127.043),(354,'지자체/개인','만세골',1,'서울특별시',10,'도봉구','서울시 도봉구 도봉동 543','주차장, 화장실 등',37.6753,127.022),(355,'지자체/마포구청','상암두레텃밭',1,'서울특별시',13,'마포구','서울시 마포구 상암동 1691','쉼터',37.5775,126.887),(356,'지자체/마포구청','삼각교육텃밭',9,'경기도',93,'고양시 덕양구','경기도 고양시 덕양구 덕은동 569-3','주차장, 화장실',37.5809,126.877),(357,'지자체/강서구청','오곡텃밭',1,'서울특별시',4,'강서구','서울시 강서구 오곡동 417-2','쉼터, 주차장, 세면장, 화장실, 흙먼지털이기계 등',37.5573,126.78),(358,'지자체/강서구청','힐링텃밭',1,'서울특별시',4,'강서구','서울시 강서구 과해동 22-2','',37.5737,126.79),(359,'지자체/송파구청','올림픽주말농장',1,'서울특별시',18,'송파구','서울시 송파구 방이동 444-18','주차장, 식수대, 화장실',37.5089,127.139),(360,'지자체/부천시청','수목원농장',9,'경기도',106,'부천시 원미구','부천시 원미구 춘의동 461','EM공급시설',37.5017,126.814),(361,'지자체/시흥시','매화동 주민자치위원회',9,'경기도',114,'시흥시','시흥시 매화동 332','쉼터',37.398,126.835),(362,'지자체/공영주말농장','도봉동 공동체 나눔텃밭',1,'서울특별시',10,'도봉구','서울특별시 도봉구 도봉동 194-31','주차장, 화장실',37.6826,127.042),(363,'지자체/공영주말농장','세대공감 텃밭',1,'서울특별시',10,'도봉구','서울특별시 도봉구 창동 산177','화장실, 쉼터',37.6432,127.048),(364,'지자체/공영주말농장','창동 도시농업 시범공원 나눔텃밭',1,'서울특별시',10,'도봉구','서울특별시 도봉구 창동 1-7','화장실, 쉼터',37.6555,127.049),(366,'지자체/민영주말농장','지도농장',1,'서울특별시',14,'서대문구','경기도 고양시 덕양구 내곡동 104-3','주차장, 화장실 등',37.6388,126.81),(367,'지자체/공영주말농장','꽃초롱텃밭',1,'서울특별시',15,'서초구','서울특별시 서초구 내곡동 287','주차장',37.4605,127.063),(368,'지자체/민영주말농장','내곡주말농장',1,'서울특별시',15,'서초구','서울특별시 서초구 내곡동 1-276','화장실, 주차장, 식수대',37.4576,127.083),(370,'지자체/민영주말농장','서초농장',1,'서울특별시',15,'서초구','서울특별시 서초구 신원동 221-2','화장실, 주차장',37.446,127.059),(371,'지자체/공영주말농장','신흥텃밭',1,'서울특별시',15,'서초구','서울특별시 서초구 내곡동 1-16','화장실, 주차장',37.4588,127.083),(372,'지자체/공영주말농장','안골텃밭',1,'서울특별시',15,'서초구','서울특별시 서초구 내곡동 305','화장실, 주차장',37.4619,127.064),(373,'지자체/민영주말농장','진주농장',1,'서울특별시',15,'서초구','서울특별시 서초구 원지동 254','화장실',37.4541,127.05),(374,'지자체/공영주말농장','청룡텃밭',1,'서울특별시',15,'서초구','서울특별시 서초구 신원동 225','화장실, 주차장',37.4471,127.06),(375,'지자체/민영주말농장','칠성농원',1,'서울특별시',15,'서초구','서울특별시 서초구 원지동 산 34-1','화장실, 주차장',37.4439,127.055),(376,'지자체/공영주말농장','석관동텃밭',1,'서울특별시',17,'성북구','서울특별시 성북구 석관동 14-5','주차장, 화장실',37.6106,127.069),(377,'지자체/공영주말농장','솔이텃밭',1,'서울특별시',18,'송파구','서울특별시 송파구 방이동 445-18','교육장, 창고, 쉼터, 화장실',37.5082,127.14),(378,'지자체/민영주말농장','신정자연주말농장',1,'서울특별시',19,'양천구','서울특별시 양천구 신월동 728-9','농기구 창고 등',37.5118,126.826),(379,'지자체/민영주말농장','지양주말농장',1,'서울특별시',19,'양천구','서울특별시 양천구 신월동 350-42','농기구 창고 등',37.5157,126.824),(380,'지자체/민영주말농장','둔촌유기농텃밭2',1,'서울특별시',2,'강동구','서울특별시 강동구 둔촌동 120-2','화장실, 식수대, 주차장',37.5247,127.145),(381,'지자체/공영주말농장','향림텃밭',1,'서울특별시',22,'은평구','서울특별시 은평구 불광동 458-1','수도시설, 화장실',37.6278,126.927),(382,'지자체/민영주말농장','홍씨네텃밭농원',1,'서울특별시',23,'종로구','서울특별시 종로구 부암동 353-1','주차장,화장실',37.5892,126.961),(383,'지자체/공영주말농장','상계3.4동 채비지 텃밭',1,'서울특별시',9,'노원구','서울특별시 노원구 상계3,4동 7-113','-',37.6806,127.089),(384,'지자체/공영주말농장','상계3.5동 채비지 텃밭',1,'서울특별시',9,'노원구','서울특별시 노원구 상계3,4동 5-16','-',37.6735,127.083),(385,'지자체/민영주말농장','상계주말농장',1,'서울특별시',9,'노원구','서울특별시 노원구 상계동 127-3','주차장, 식수대, 화장실',37.668,127.07),(386,'지자체/개인','공동주택인접텃밭',10,'경상남도',142,'양산시','경상남도 양산시 삼성동 호계동144','',35.3788,129.079),(387,'지자체/개인','농장형주말텃밭',10,'경상남도',142,'양산시','경상남도 양산시 삼성동 호계동522','',35.3611,129.071),(388,'지자체/기타','폐공가텃밭',3,'대구광역시',43,'달서구','대구광역시 달서구 감삼동 41-6','-',35.8542,128.541),(389,'지자체/주말농장','LH율하나눔텃밭',3,'대구광역시',44,'동구','대구광역시 동구 율하동 1413','-',35.8606,128.7),(390,'지자체/주말농장','안심농장',3,'대구광역시',44,'동구','대구광역시 동구 금강동 272-2','-',35.8544,128.734),(391,'지자체/주말농장','관음농장',3,'대구광역시',45,'북구','대구광역시 북구 관음동 833','물탱크',35.9374,128.541),(392,'지자체/주말농장','국우농장',3,'대구광역시',45,'북구','대구광역시 북구 도남동 280','-',35.9649,128.597),(393,'지자체/주말농장','도남농장',3,'대구광역시',45,'북구','대구광역시 북구 도남동 714-3','물탱크,쉼터',35.959,128.583),(394,'지자체/주말농장','좋은이웃텃밭',3,'대구광역시',45,'북구','대구광역시 북구 도남동 723-1','물탱크,쉼터',35.9601,128.583),(395,'지자체/공동경작 및 복지사업운영','가천농장',3,'대구광역시',47,'수성구','대구광역시 수성구 가천동 109-2','농업용수',35.856,128.684),(396,'지자체/공동경작 및 복지사업운영','성동농장',3,'대구광역시',47,'수성구','대구광역시 수성구 성동 436-14','농업용수',35.8508,128.722),(397,'지자체/주말농장','조일골농장',3,'대구광역시',47,'수성구','대구광역시 수성구 지산동 16-1','주차장, 화장실, 농업용수',35.8309,128.648),(398,'지자체/주말','강변힐링 주말농장',3,'대구광역시',49,'달성군','대구광역시 달성군 다사읍 이천리 661','쉼터 등',35.8769,128.458),(399,'지자체/주말','달성신당정보화마을 주말농장',3,'대구광역시',49,'달성군','대구광역시 달성군 옥포면 신당리 821','쉼터',35.8013,128.439),(400,'지자체/주말','마비정 초심농장',3,'대구광역시',49,'달성군','대구광역시 달성군 화원읍 본리리 1145-11','쉼터',35.777,128.534),(401,'지자체/주말','마비정벽화마을 주말농장',3,'대구광역시',49,'달성군','대구광역시 달성군 화원읍 본리리 1080-2','쉼터',35.7798,128.532),(402,'지자체/주말','문양역 마천산 주말농장',3,'대구광역시',49,'달성군','대구광역시 달성군 다사읍 문양리 431','쉼터',35.8671,128.437),(403,'지자체/주말','비슬육묘농장',3,'대구광역시',49,'달성군','대구광역시 달성군 옥포면 교항리 2710','쉼터, 화장실 등',35.7832,128.452),(404,'지자체/주말','아가원 주말농장',3,'대구광역시',49,'달성군','대구광역시 달성군 옥포면 기세리 794','쉼터',35.767,128.486),(405,'지자체/주말','육신사 성원농장',3,'대구광역시',49,'달성군','대구광역시 달성군 하빈면 묘리 778','-',35.9094,128.416),(406,'지자체/주말농장','계양테마농장',4,'인천광역시',50,'계양구','인천광역시 계양구 박촌동 6-8','화장실,샤워장, 주차장 매점',37.561,126.746),(407,'지자체/주말농장','귤현웰빙주말농장',4,'인천광역시',50,'계양구','인천광역시 계양구 귤현동 349 외','화장실, 주차장, 휴게실',37.5633,126.741),(408,'지자체/주말농장','씨아이플라워 주말농장',4,'인천광역시',50,'계양구','인천광역시 계양구 병방동 217-4','주차장,화장실,모종판매장',37.5431,126.754),(409,'지자체/체험농장','솔머리행복텃밭',5,'광주광역시',60,'광산구','광주광역시 광산구 소촌동 420','원두막, 관정',35.1463,126.793),(410,'지자체/주말농장','행복둥지텃밭 1호',5,'광주광역시',60,'광산구','광주광역시 광산구 도천동 522','없음',35.2202,126.816),(411,'지자체/주말농장','행복둥지텃밭 2호',5,'광주광역시',60,'광산구','광주광역시 광산구 비아동 441','수도시설',35.2157,126.818),(412,'지자체/주말농장','해피팜주말농장',5,'광주광역시',63,'북구','광주광역시 북구 장등동 803','수도, 쉼터,그네등',35.2065,126.932),(413,'지자체/주말농장','마제텃밭',5,'광주광역시',64,'서구','광주광역시 서구 금호동 60-14','창고, 휴게실, 관정',35.1295,126.863),(414,'지자체/주말농장','봉산주말농장',5,'광주광역시',64,'서구','광주광역시 서구 용두동 656-8','그늘막, 관정',35.0953,126.814),(415,'지자체/주말농장','풍암호수공원주말농장',5,'광주광역시',64,'서구','광주광역시 서구 풍암동 556-1','창고, 휴게실, 관정',35.1298,126.868),(416,'지자체/주말농장','성포주말농장',9,'경기도',116,'안산시 상록구','경기도 안산시 상록구 이동  299-3','수도시설,쉼터',37.3148,126.85),(417,'지자체/주말농장','은성주말농장',9,'경기도',116,'안산시 상록구','경기도 안산시 상록구 장상동 109','주차장,화장실',37.3543,126.869),(418,'지자체/주말농장','팝콘주말농장',9,'경기도',116,'안산시 상록구','경기도 군포시 부곡동 129-3','주차장,화장실',37.337,126.935),(419,'지자체/주말농장','부락산텃밭',9,'경기도',129,'평택시','경기도 평택시 장안동 94-1','농막화장실',37.0554,127.087),(420,'지자체/주말농장','전원주말농장',9,'경기도',129,'평택시','경기도 평택시 지산동 590-1','수도시설',37.0875,127.049),(421,'지자체/주말농장','새싹주말농장',9,'경기도',132,'화성시','경기도 화성시 진안동 219-1','수도,쉼터,화장실',37.2198,127.04),(422,'지자체/주말농장','웰빙주말농장',9,'경기도',132,'화성시','경기도 화성시 진안동 692-2','수도,쉼터,화장실',37.221,127.037),(423,'지자체/주말농장','햇님주말농장',9,'경기도',132,'화성시','경기도 화성시 진안동 35-24','수도,쉼터,화장실',37.2244,127.036),(424,'지자체/주말농장','힐링캠프주말농장',9,'경기도',132,'화성시','경기도 화성시 진안동 692','수도,쉼터,화장실',37.2208,127.037),(425,'지자체/공영주말농장','교동리',9,'경기도',134,'양평군','경기도 양평군 양서면 부용리 21','그늘막,급수시설,주차장,화장실',37.5564,127.357),(426,'지자체/공영주말농장','부용리',9,'경기도',134,'양평군','경기도 양평군 양서면 부용리 582-1','그늘막,급수시설,주차장,화장실',37.5525,127.337),(427,'지자체/공영주말농장','수능리',9,'경기도',134,'양평군','경기도 양평군 서종면 수능리 395','그늘막,급수시설,주차장,화장실',37.6001,127.371),(428,'지자체/공영주말농장','귀여리',9,'경기도',98,'광주시','경기도 광주시 남종면 귀여리 393-2','그늘막,급수시설,주차장,화장실',37.5036,127.326),(429,'지자체/공영주말농장','도마리',9,'경기도',98,'광주시','경기도 광주시 퇴촌면 도마리 200','그늘막,급수시설,주차장,화장실',37.467,127.277),(430,'지자체/공영주말농장','삼성리',9,'경기도',98,'광주시','경기도 광주시 남종면 삼성리 422','그늘막,급수시설,주차장,화장실',37.474,127.274),(431,'지자체/공영주말농장','지월리',9,'경기도',98,'광주시','경기도 광주시 초월읍 지월리 680','그늘막,급수시설,주차장,화장실',37.404,127.288),(432,'지자체/공영주말농장','삼봉리',9,'경기도',102,'남양주시','경기도 남양주시 조안면 삼봉리 331-3','그늘막,급수시설,주차장,화장실',37.5891,127.333),(433,'지자체/공영주말농장','송촌약수터',9,'경기도',102,'남양주시','경기도 남양주시 조안면 송촌리 964','그늘막,급수시설,주차장,화장실',37.5784,127.313),(434,'지자체/주말농장','개나리주말농장',9,'경기도',105,'부천시 오정구','경기도 부천시 오정구 고강동 187-8','관수시설',37.5319,126.81),(435,'지자체/주말농장','너른마당주말농장',9,'경기도',105,'부천시 오정구','경기도 부천시 오정구 고강동 582','주차장',37.5228,126.824),(436,'지자체/주말농장','웰빙주말농장',9,'경기도',112,'수원시 장안구','경기도 수원시 장안구 상광교동 193','주차장',37.3293,127.021),(437,'지자체/시민농장','천천농장',9,'경기도',112,'수원시 장안구','경기도 수원시 장안구 천천동  388-2','화장실,주차장',37.2902,126.975),(438,'지자체/주말농장','크로바농장',9,'경기도',112,'수원시 장안구','경기도 수원시 장안구 파장동 365-4','',37.3207,127.007),(439,'지자체/주말농장','한솔주말농장',9,'경기도',112,'수원시 장안구','경기도 수원시 장안구 상광교동 284-1','',37.3259,127.014),(440,'지자체/주말농장','단원주말농장',9,'경기도',115,'안산시 단원구','경기도 안산시 단원구 초지동 747','주차장,화장실',37.3049,126.806),(441,'지자체/주말농장','신길주말농장',9,'경기도',115,'안산시 단원구','경기도 안산시 단원구 신길동 1380','주차장,화장실',37.3394,126.762),(442,'지자체/주말농장','제2화랑주말농장',9,'경기도',115,'안산시 단원구','경기도 안산시 단원구 초지동 667','주차장,화장실',37.3265,126.814),(443,'지자체/주말농장','나들목주말농장',9,'경기도',116,'안산시 상록구','경기도 안산시 상록구 양상동 318','주차장,화장실',37.3407,126.853),(444,'지자체/주말농장','동막골주말농장',9,'경기도',116,'안산시 상록구','경기도 안산시 상록구 장상동 341','주차장,화장실',37.3487,126.881),(445,'지자체/주말농장','바람들이농장',9,'경기도',116,'안산시 상록구','경기도 군포시 부곡동  262-1','주차장,화장실',37.3367,126.936),(446,'지자체/주말농장','서해농장',9,'경기도',116,'안산시 상록구','경기도 안산시 상록구 건건동  956-2','주차장',37.3048,126.906),(447,'지자체/주말농장','도시애원두막농장',9,'경기도',106,'부천시 원미구','경기도 부천시 원미구 역곡동 184-1','주차장',37.4916,126.805),(448,'지자체/주말농장','산울림농장',9,'경기도',106,'부천시 원미구','경기도 부천시 원미구 춘의동 343','주차장',37.499,126.807),(449,'지자체/주말농장     체험교육농장','성남시민농원',9,'경기도',109,'성남시 중원구','경기도 성남시 중원구 성남동 4823','화장실,정자,공용농기구',37.4261,127.124),(450,'지자체/시민농장','고색농장',9,'경기도',110,'수원시 권선구','경기도 수원시 권선구 고색동  7-175','화장실,주차장',37.2572,126.984),(451,'지자체/주말농장','늘푸른주말농장',9,'경기도',110,'수원시 권선구','경기도 수원시 권선구 호매실동 365-4','',37.2649,126.96),(452,'지자체/시민농장','당수농장',9,'경기도',110,'수원시 권선구','경기도 수원시 권선구 당수동 434','화장실,주차장',37.2846,126.942),(453,'지자체/주말농장','도토리시민농장',9,'경기도',110,'수원시 권선구','경기도 수원시 권선구 호매실동 919','주차장',37.2619,126.942),(454,'지자체/주말농장','광교주말농장',9,'경기도',112,'수원시 장안구','경기도 수원시 장안구 하광교동 298-1','',37.3105,127.026),(455,'지자체/주말농장','들꽃농장',9,'경기도',112,'수원시 장안구','경기도 수원시 장안구 상광교동 148-2','화장실, 주차장',37.327,127.016),(456,'지자체/주말농장','연꽃주말농장',9,'경기도',112,'수원시 장안구','경기도 수원시 장안구 하광교동 260','',37.3124,127.025),(457,'지자체/세종청사관리소,세종농협','세종청사텃밭사랑',17,'세종특별자치시',249,'세종특별자치시','아름동(세종청사8동옆 17주차장)','관수시설,화장실,주차장,휴게시설',36.5044,127.268),(458,'지자체/개인','세종나리마을',17,'세종특별자치시',249,'세종특별자치시','연서면  청라1길 6-6','관수시설, 화장실, 주차장. 휴게시설',36.588,127.197),(459,'지자체/개인','청송리웰촌남새밭',17,'세종특별자치시',249,'세종특별자치시','전동면  청송리 19','관수시설, 화장실, 휴게시설',36.6724,127.258),(460,'지자체/민간','개화텃밭농장',1,'서울특별시',4,'강서구','강서구 개화동 643-3','쉼터, 주차장, 세면장, 화장실 등',37.5723,126.794),(461,'지자체/민간','행복한도시농장',1,'서울특별시',4,'강서구','강서구 오쇠동 151-11','쉼터, 주차장, 세면장, 화장실 등',37.5395,126.798),(462,'지자체/민간','초록향기',1,'서울특별시',10,'도봉구','도봉구 도봉동 384-1','주차장, 화장실',37.6898,127.04),(463,'지자체/민간','영이네 주말농장',1,'서울특별시',10,'도봉구','도봉구 방학동 533-3','주차장, 화장실',37.6596,127.021),(464,'지자체/민간','지심원',1,'서울특별시',15,'서초구','서초구 신원동 169-1','화장실',37.4427,127.061),(465,'지자체/민간','염곡주말농장',1,'서울특별시',15,'서초구','서초구 염곡동 26','화장실, 주차장',37.4617,127.057),(466,'지자체/민간','청계진주주말농장',1,'서울특별시',15,'서초구','서초구 원지동 537','화장실, 주차장',37.4348,127.062),(467,'지자체/민간','성심농원',1,'서울특별시',15,'서초구','서초구 원지동 560-2','주차장, 식수대, 화장실',37.4367,127.062),(468,'지자체/공영주말농장','성북동어린이텃밭',1,'서울특별시',17,'성북구','서울특별시 성북구 성북동 193-8','자재창고',37.5901,127.002),(469,'지자체/민간','둔굴주말농장',1,'서울특별시',18,'송파구','송파구 방이동 433-3','주차장, 식수대, 화장실',37.5172,127.141),(470,'지자체/민간','태현주말농장',1,'서울특별시',18,'송파구','송파구 방이동 436-52','주차장, 식수대, 화장실',37.5137,127.142),(471,'지자체/민간','누리농원',1,'서울특별시',25,'중랑구','중랑구 신내동 256-13','주차장, 식수대, 화장실',37.6191,127.11),(472,'민간단체','에코농장',3,'대구광역시',44,'동구','대구 동구 팔공산로254길 123 (미곡동)','',35.9724,128.69),(473,'개인','산천초목주말농장',9,'경기도',94,'고양시 일산동구','경기 고양시 일산동구 백석동 1127-2','주차장, 수세식 화장실, 농장전체 발스프링쿨러설치, 그늘막 휴식공간',37.6311,126.792),(474,'지자체/주말농장','행복텃밭',4,'인천광역시',56,'연수구','인천광역시 연수구 송도동 107-1','창고, 화장실',37.3833,126.635),(475,'민간단체','도시텃밭',5,'광주광역시',64,'서구','광주광역시 서구 양동 406','-',35.1566,126.894),(476,'지자체/주말농장','대화 나눔텃밭 1',6,'대전광역시',65,'대덕구','대전광역시 대덕구 대화동 251-3','-',36.3641,127.41),(477,'지자체/주말농장','대화 나눔텃밭 2',6,'대전광역시',65,'대덕구','대전광역시 대덕구 대화동 35-699','-',36.3661,127.414),(478,'지자체/주말농장','대화 나눔텃밭 3',6,'대전광역시',65,'대덕구','대전광역시 대덕구 대화동 35-102','-',36.3654,127.416),(479,'지자체/주말농장','덕암 나눔텃밭 1',6,'대전광역시',65,'대덕구','대전광역시 대덕구 덕암동 258-3','-',36.4328,127.419),(480,'지자체/주말농장','덕암 나눔텃밭 2',6,'대전광역시',65,'대덕구','대전광역시 대덕구 덕암동 22-5','-',36.4434,127.422),(481,'지자체/주말농장','덕암 나눔텃밭 3',6,'대전광역시',65,'대덕구','대전광역시 대덕구 상서동 153-1','-',36.4273,127.425),(482,'지자체/주말농장','목상 나눔텃밭 1',6,'대전광역시',65,'대덕구','대전광역시 대덕구 목상동 875','-',36.4444,127.412),(483,'지자체/주말농장','공도텃밭농원',9,'경기도',117,'안성시','경기도 안성시 공도읍 275-17','수도시설3, 화장실, 농기구무상\r\n대여창고2, 쉼터1, 놀이기구\r\n호박터널, 주차장',37.0036,127.165),(484,'지자체/주말농장','현수동텃밭농원',9,'경기도',117,'안성시','경기도 안성시 현수동 168번지','수도시설3,화장실, 농기구무상\r\n대여 창고2, 쉼터2, 호박터널',36.9988,127.284),(485,'개인','숲속의포도원 주말농장',9,'경기도',118,'안양시 동안구','경기도 안양시 동안구 관양동 산18-1','주차장',37.4048,126.973),(486,'개인','예술공원 주말농장',9,'경기도',119,'안양시 만안구','경기도 안양시 만안구 석수동 221-1','주차장,그늘막',37.42,126.918),(487,'개인','유원주말농장',9,'경기도',119,'안양시 만안구','경기도 안양시 만안구 석수동 145-2','주차장,그늘막',37.4252,126.915),(488,'개인','커뮤니티농장',9,'경기도',119,'안양시 만안구','경기도 안양시 만안구 박달동 378-2','주차장,그늘막\r\n화장실',37.3893,126.884),(489,'개인','남경수목원',9,'경기도',120,'양주시','경기도 양주시 삼숭로 66번길 9-29','화장실,주차장,휴게실',37.802,127.102),(490,'민간단체','나눔텃밭',9,'경기도',122,'용인시 기흥구','경기도 용인시 기흥구 영덕동 1099','주차장, 교육장, 화장실',37.2735,127.088),(491,'지자체/주말농장','용인시민농장',9,'경기도',124,'용인시 처인구','경기도 용인시 처인구 마평동 573-8','주차장, 화장실, 원두막',37.228,127.215),(492,'지자체/주말농장','이천시 나눔텃밭',9,'경기도',127,'이천시','경기도 이천시 모가면 사실로 720','원두막, 퍼고라, 주차장, 화장실',37.1759,127.44),(493,'민간단체','하남도시농사협동조합주말농장',9,'경기도',131,'하남시','경기도 하남시 교산동 174-2','농기구보관함, 관수시설 등',37.5269,127.207),(494,'개인','금당주말농장',9,'경기도',132,'화성시','경기도 화성시 마도면 금당리 74','수도,쉼터,화장실',37.1923,126.741),(495,'개인','비봉쌍학리주말농장',9,'경기도',132,'화성시','경기도 화성시 비봉면 쌍학리 812-4','수도,쉼터,화장실',37.2226,126.901),(496,'개인','쩡대농장',9,'경기도',132,'화성시','경기도 화성시 정남면 괘랑리 210-1','수도,쉼터,화장실',37.1823,126.993),(497,'개인','청산농원',9,'경기도',132,'화성시','경기도 화성시 안녕동 71-480','수도,쉼터',37.2001,127.01),(498,'개인','행복텃밭',9,'경기도',132,'화성시','경기도 화성시 매송면 어천리 606-2','수도,쉼터,화장실',37.2472,126.904),(499,'민간단체','물빛누리',9,'경기도',134,'양평군','경기도 양평군 단월면 봉상리 500-2(수미길52번길 2-1)','주차장 등',37.5153,127.648),(500,'지자체/공영주말농장','원당역',9,'경기도',93,'고양시 덕양구','경기도 고양시 덕양구 성사동 469','그늘막,급수시설,주차장,화장실',37.6602,126.85),(501,'지자체/주말농장','소하시민주말농장',9,'경기도',97,'광명시','경기도 광명시 소하동 1283번지','주차장, 화장실, 테이블, 쉼터 등',37.4552,126.882),(502,'지자체/주말농장','신촌시민주말농장',9,'경기도',97,'광명시','경기도 광명시 소하동 1344번지','주차장, 화장실, 테이블, 쉼터 등',37.4458,126.894),(503,'지자체/공영주말농장','하번천리',9,'경기도',98,'광주시','경기도 광주시 남한산성면 하번천리 120','그늘막,급수시설,주차장,화장실',37.4389,127.288),(504,'지자체/주말농장','용계',6,'대전광역시',68,'유성구','대전광역시 유성구 용계동 452-1','수도, 쉼터',36.3253,127.315),(505,'지자체/주말농장','죽동',6,'대전광역시',68,'유성구','대전광역시 유성구 죽   동 228-6','수도, 쉼터',36.3002,127.32),(506,'지자체/자율형','유일나눔텃밭',6,'대전광역시',69,'중구','대전광역시 중구 유천동 339','주차장',36.3156,127.389),(507,'지자체/주말농장','퇴직자 도시농장',7,'울산광역시',70,'남구','울산광역시 남구 신정동 993-10','주차장',35.5202,129.301),(508,'지자체/주말농장','동구 주말농장',7,'울산광역시',71,'동구','울산광역시 동구 일산동 산43-2','원두막 1, 평상 2, 물통2',35.4888,129.438),(509,'민간단체','달천도시텃밭',7,'울산광역시',72,'북구','울산광역시 북구 달천동 119-7','화장실',35.6315,129.322),(510,'민간단체','대안도시텃밭',7,'울산광역시',72,'북구','울산광역시 북구 대안동 577','휴게실',35.6625,129.409),(511,'민간단체','양정도시텃밭',7,'울산광역시',72,'북구','울산광역시 북구 양정동 산31-1','',35.5574,129.392),(512,'지자체/주말농장','중구 주말농장',7,'울산광역시',73,'중구','울산광역시 중구 약사동 1','화장실',35.5969,129.323),(513,'민간단체','원평팜스테이',8,'강원도',80,'춘천시','강원도 춘천시 사북면 원평리 470-1','주차장,식수대,화장실',38.0044,127.629),(514,'지자체/주말농장','와부 텃밭',9,'경기도',102,'남양주시','경기도 남양주시 와부읍 월문리 371-3','없음',37.6085,127.263),(515,'지자체/주말농장','진건텃밭',9,'경기도',102,'남양주시','경기도 남양주시 진건읍 진관리 41번지','화장실, 급수, 그늘막',37.6496,127.176),(517,'지자체/주말농장','호평동 텃밭',9,'경기도',102,'남양주시','경기도 남양주시 호평동 369-8','없음',37.6676,127.248),(518,'지자체/체험','화도 텃논(밭)',9,'경기도',102,'남양주시','경기도 남양주시 화도읍 금남리 215-10','없음',37.6501,127.365),(519,'개인','OK주말농장',9,'경기도',105,'부천시 오정구','경기도 부천시 오정구 고강동 200-9','주차장',37.5313,126.813),(520,'지자체/공영주말농장','논곡동',9,'경기도',114,'시흥시','경기도 시흥시 논곡동 22-2','그늘막,급수시설,주차장,화장실',37.4021,126.853),(521,'지자체/주말농장','화랑주말농장',9,'경기도',115,'안산시 단원구','경기도 안산시 단원구 초지동 666-2','주차장,화장실',37.3203,126.811),(522,'개인','개인텃밭',17,'세종특별자치시',249,'세종특별자치시','금남면 영대리 335','관수시설',36.4618,127.339),(523,'개인','개인텃밭',17,'세종특별자치시',249,'세종특별자치시','조치원읍  남리 56','관수시설',36.5968,127.299),(524,'개인','개인텃밭',17,'세종특별자치시',249,'세종특별자치시','조치원읍 남리 87-2','관수시설',36.5942,127.303),(525,'지자체/주말농장','법2 나눔텃밭 1',6,'대전광역시',65,'대덕구','대전광역시 대덕구 법동 12','-',36.3738,127.44),(526,'지자체/주말농장','비래 나눔텃밭 2',6,'대전광역시',65,'대덕구','대전광역시 대덕구 비래동 239-1','-',36.3618,127.45),(527,'지자체/주말농장','비래 나눔텃밭 3',6,'대전광역시',65,'대덕구','대전광역시 대덕구 비래동 309-11','-',36.3602,127.453),(528,'지자체/주말농장','비래 나눔텃밭 4',6,'대전광역시',65,'대덕구','대전광역시 대덕구 비래동 14-3','-',36.3555,127.457),(529,'지자체/주말농장','석봉 나눔텃밭 1',6,'대전광역시',65,'대덕구','대전광역시 대덕구 석봉동 774','수도',36.4508,127.423),(530,'지자체/주말농장','석봉 나눔텃밭 2',6,'대전광역시',65,'대덕구','대전광역시 대덕구 석봉동 186-19','-',36.4475,127.427),(531,'지자체/주말농장','송촌 나눔텃밭 1-1',6,'대전광역시',65,'대덕구','대전광역시 대덕구 법동 46-4','수도',36.373,127.431),(532,'지자체/주말농장','송촌 나눔텃밭 1-2',6,'대전광역시',65,'대덕구','대전광역시 대덕구 송촌동 77-2','수도',36.3701,127.44),(533,'지자체/주말농장','송촌 나눔텃밭 2',6,'대전광역시',65,'대덕구','대전광역시 대덕구 송촌동 51-3','-',36.3682,127.446),(534,'지자체/주말농장','신탄진 나눔텃밭 1',6,'대전광역시',65,'대덕구','대전광역시 대덕구 신탄진동 24-2','-',36.4516,127.438),(535,'지자체/주말농장','중리 나눔텃밭 1',6,'대전광역시',65,'대덕구','대전광역시 대덕구 중리동 383-11','-',36.363,127.424),(536,'지자체/주말농장','중리 나눔텃밭 2',6,'대전광역시',65,'대덕구','대전광역시 대덕구 중리동 355-9','-',36.36,127.419),(537,'지자체/주말농장','중리 나눔텃밭 3',6,'대전광역시',65,'대덕구','대전광역시 대덕구 중리동 115','-',36.3646,127.433),(538,'지자체/주말농장','회덕 나눔텃밭 1',6,'대전광역시',65,'대덕구','대전광역시 대덕구 읍내동 247-3','-',36.3768,127.422),(539,'지자체/주말농장','회덕 나눔텃밭 2',6,'대전광역시',65,'대덕구','대전광역시 대덕구 읍내동 357-4','-',36.3737,127.419),(540,'지자체/주말농장','갈마텃밭',6,'대전광역시',67,'서구','대전광역시 서구 갈마동 424-5','-',36.3426,127.365),(541,'지자체/주말농장','도안텃밭',6,'대전광역시',67,'서구','대전광역시 서구 도안동 1365','-',36.3183,127.347),(542,'지자체/주말농장','관평',6,'대전광역시',68,'유성구','대전광역시 유성구 관평동 577-2','수도, 쉼터',36.4184,127.38),(543,'지자체/주말농장','법1 나눔텃밭 1',6,'대전광역시',65,'대덕구','대전광역시 대덕구 법동 288','수도',36.3684,127.423),(544,'지자체/주말농장','외삼',6,'대전광역시',68,'유성구','대전광역시 유성구 외삼동 338-7','수도, 쉼터',36.3996,127.308),(545,'지자체/공영주말농장','도봉동 친환경 영농체험장',1,'서울특별시',10,'도봉구','서울특별시 도봉구 도봉동 8','화장실, 쉼터',37.6919,127.047),(546,'지자체/공영주말농장','길음뉴타운주말농장',1,'서울특별시',17,'성북구','서울특별시 성북구 길음동 1285-8','원두막,자재창고',37.6089,127.02),(547,'지자체/공영주말농장','정릉3동텃밭',1,'서울특별시',17,'성북구','서울특별시 성북구 정릉동 908-4','자재창고',37.6039,127.001),(548,'개인','오륜농장',1,'서울특별시',18,'송파구','서울특별시 송파구 방이동  444-14','-',37.509,127.139),(550,'지자체/기타(도시텃밭)','문래동 공공공지 도시텃밭',1,'서울특별시',20,'영등포구','서울특별시 영등포구 문래동  55-6','화장실, 원두막, 족구장',37.5177,126.899),(552,'지자체/공영주말농장','꿈이닿은농장',1,'서울특별시',4,'강서구','서울특별시 강서구 오쇠동 102-4','화장실, 원두막',37.5444,126.791),(553,'지자체/공영주말농장','청룡산 마을텃밭',1,'서울특별시',5,'관악구','서울특별시 관악구 봉천동 556-90','가재보',37.4768,126.949),(554,'지자체/공영주말농장','광나루텃밭',1,'서울특별시',6,'광진구','서울특별시 광진구 광장동 401-24','농기구보관함,게시판,야외용벤치 등',37.5452,127.1),(555,'지자체/공영주말농장','광장동텃밭',1,'서울특별시',6,'광진구','서울특별시 광진구 광장동 582-3','주차장,농기구보관함,\r\n게시판,야외용벤치 등',37.5445,127.099),(556,'지자체/공영주말농장','아차산텃밭',1,'서울특별시',6,'광진구','서울특별시 광진구 광장동 378','농기구보관함,게시판,야외용벤치 등',37.5494,127.101),(557,'지자체/공영주말농장','공동체텃밭',1,'서울특별시',7,'구로구','서울특별시 구로구 궁동 59','급수시설,주차장,쉼터',37.5023,126.826),(558,'지자체/공영주말농장','궁동1구역',1,'서울특별시',7,'구로구','서울특별시 구로구 궁동 4','관리실,급수시설,\r\n화장실',37.5059,126.829),(559,'지자체/공영주말농장','궁동2구역',1,'서울특별시',7,'구로구','서울특별시 구로구 궁동 53-2','관리실,급수시설,\r\n화장실,주차장,쉼터',37.5009,126.827),(560,'지자체/공영주말농장','궁동3구역',1,'서울특별시',7,'구로구','서울특별시 구로구 궁동 70-1','관리실,급수시설,\r\n화장실,주차장,쉼터',37.5017,126.824),(561,'지자체/공영주말농장','궁동4구역',1,'서울특별시',7,'구로구','서울특별시 구로구 궁동 125','관리실,급수시설,\r\n화장실',37.5018,126.832),(562,'지자체/공영주말농장','고갯마루텃밭',1,'서울특별시',9,'노원구','서울특별시 노원구 상계동 128-1','-',37.6679,127.071),(563,'지자체/공영주말농장','불암허브공원텃밭',1,'서울특별시',9,'노원구','서울특별시 노원구 상계동  95-372','-',37.664,127.08),(564,'지자체/공영주말농장','중계4동 지렁이텃밭',1,'서울특별시',9,'노원구','서울특별시 노원구 중계동 453-41','-',37.6582,127.072),(565,'개인','도심속웰빙지',10,'경상남도',137,'거제시','경상남도 거제시 장평동 127','',34.8879,128.606),(566,'개인','한림텃밭',10,'경상남도',138,'김해시','경상남도 김해시 한림면 신천리471-11','쉼터, 화장실, 관수시설, 주차장',35.2726,128.833),(567,'개인','주말체험농장',10,'경상남도',143,'진주시','경상남도 진주시 집현면 장흥리 350','주차장',35.2273,128.105),(568,'지자체/체험농장','녹색체험 텃밭',10,'경상남도',147,'거창군','경상남도 거창군 거창읍 대평리 1345','식수대',35.6741,127.927),(569,'민간단체','혁신도시 공원텃밭',12,'전라남도',182,'나주시','전라남도 나주시 빛가람동 536외 2개소','',35.0102,126.776),(570,'개인','고인돌텃밭농장',12,'전라남도',202,'화순군','전라남도 화순군 도곡면 월곡리 672','주차장',34.987,126.913),(571,'개인','능주정보화마을텃밭',12,'전라남도',202,'화순군','전라남도 화순군 능주면 백암리','주차장',35.0205,126.971),(572,'개인','힐링가족텃밭',12,'전라남도',202,'화순군','전라남도 화순군 화순읍 교리 247','비가림쉼터',35.0609,126.98),(573,'개인','힐링텃밭',12,'전라남도',202,'화순군','전라남도 화순군 화순읍 삼천리 48','비가림쉼터',35.0458,126.993),(574,'개인','신봉마을텃밭',13,'전라북도',214,'완주군','전라북도 완주군 봉동읍 둔산리 69','급수시설, 정자, 주차장',35.9665,127.134),(575,'개인','남일텃밭',16,'충청북도',237,'청주시 상당구','충청북도 청주시 상당구 남일면 효촌리 98','하우스,주차장,이동식화장실',36.5894,127.504),(576,'개인','공고텃밭',16,'충청북도',238,'청주시 흥덕구','충청북도 청주시 흥덕구 석곡동 25-1','하우스,주차장,이동식화장실',36.6098,127.43),(577,'개인','공예골텃밭',16,'충청북도',238,'청주시 흥덕구','충청북도 청주시 흥덕구 석곡동 153','하우스,주차장,이동식화장실',36.6041,127.425),(578,'개인','원평텃밭',16,'충청북도',238,'청주시 흥덕구','충청북도 청주시 흥덕구 원평동 105','하우스,주차장,이동식화장실',36.6759,127.413),(579,'개인','저수지텃밭',16,'충청북도',238,'청주시 흥덕구','충청북도 청주시 흥덕구 석곡동 238','하우스,주차장,이동식화장실',36.6055,127.428),(580,'개인','휴암텃밭',16,'충청북도',238,'청주시 흥덕구','충청북도 청주시 흥덕구 휴암동 412','하우스,주차장,이동식화장실',36.6188,127.401),(581,'개인','외평텃밭',16,'충청북도',248,'청주시 청원구','충청북도  외평동 668-3','하우스,주차장,이동식화장실',36.7111,127.476),(582,'개인','주성텃밭',16,'충청북도',248,'청주시 청원구','충청북도  주성동 183-1','하우스,주차장,이동식화장실',36.6771,127.5),(583,'개인','대광 주말농장',3,'대구광역시',49,'달성군','대구광역시 달성군 다사읍 문양리 960','-',35.8668,128.425),(584,'지자체/기타유형','상자텃밭교실',4,'인천광역시',54,'부평구','인천광역시 부평구 갈산동 403','주차장, 화장실',37.5092,126.73),(585,'개인','꽃뫼주말농장',4,'인천광역시',55,'서구','인천광역시 서구 시천동 3-16','주차장,화장실,체험장,쉼터',37.5647,126.696),(586,'개인','불로농원',4,'인천광역시',55,'서구','인천광역시 서구 불로동 4-1','주차장,화장실,쉼터',37.6117,126.676),(587,'지자체/주말농장','사랑텃밭',4,'인천광역시',56,'연수구','인천광역시 연수구 연수동 581-2','창고, 화장실',37.4187,126.691),(588,'지자체/주말농장','선학텃밭',4,'인천광역시',56,'연수구','인천광역시 연수구 선학동 216-3','창고, 화장실',37.4283,126.701),(589,'개인','하우스프레 관광영농조합법인',9,'경기도',93,'고양시 덕양구','고양시 덕양구 서삼릉길 120','',37.6634,126.86),(590,'개인','푸른꽃화원 주말농장',9,'경기도',93,'고양시 덕양구','고양시 덕양구 화전동 529','',37.6072,126.867),(591,'개인','화정주말농장',9,'경기도',93,'고양시 덕양구','덕양구 대장동 561-4','',37.6342,126.824),(594,'지자체/남구청','주말가족농장',7,'울산광역시',70,'남구','울산시 남구 옥동 2-1','',35.5166,129.3),(595,'개인','청매실농장',2,'부산광역시',26,'강서구','부산 강서구 대저1동 2051','',35.2096,128.963),(596,'개인','배꽃농장',2,'부산광역시',26,'강서구','부산 강서구 강동동 193-4','',35.2223,128.939),(597,'개인','삼근농장',2,'부산광역시',27,'금정구','부산 금정구 두구동 629-10','',35.2863,129.106),(598,'개인','해바라기 농장',2,'부산광역시',26,'강서구','부산 강서구 대저1동 2813-1','',35.207,128.973),(599,'개인','솥뚜껑토마토정보화마을농장',2,'부산광역시',26,'강서구','부산 강서구 대저2동 551-1','',35.165,128.921),(600,'개인','행복두배목곡농원',2,'부산광역시',41,'기장군','부산 기장군 일광면 이천리 612-1','',35.274,129.239),(601,'개인','와여다복솔마을',2,'부산광역시',41,'기장군','부산 기장군 철마면 와여리 521번지','',35.2771,129.15),(602,'개인','선도농장',2,'부산광역시',41,'기장군','부산 기장군 철마면 임기리 687-1','',35.3208,129.117),(603,'개인','꿈그린주말농장',2,'부산광역시',41,'기장군','부산 기장군 정관면 두명리 427-1번지','',35.3499,129.149),(604,'개인','웰빙주말농장',2,'부산광역시',27,'금정구','부산시 금정구 선동 238번지','',35.2615,129.113),(605,'개인','유기농자연농원',2,'부산광역시',26,'강서구','부산시 강서구 대저1동 2012번지','',35.2098,128.961),(616,'개인','단 비 네',1,'서울특별시',10,'도봉구','서울시 도봉구 도봉동 431-5','',37.6798,127.036),(618,'지자체/성동구청','서울숲 도시농업 체험장',1,'서울특별시',16,'성동구','서울시 성동구 성수1가 685','',37.5449,127.039),(619,'지자체/성동구청','살곶이체육공원 도시농업체험장',1,'서울특별시',16,'성동구','서울시 성동구 사근동 104','',37.5549,127.05),(621,'지자체/동작구청','동작주말농장',1,'서울특별시',12,'동작구','서울시 동작구 대방동 340-4','',37.5125,126.927),(624,'개인','자야농장',1,'서울특별시',18,'송파구','서울시 송파구 방이동 433-11','',37.5159,127.142),(627,'지자체/수원시','평리동 시민농장',9,'경기도',110,'수원시 권선구','수원시 권선구 고색동 556 외 1','',37.2359,126.989),(633,'지자체/부천시청','실버농장',9,'경기도',106,'부천시 원미구','부천시 원미구 춘의동 302','',37.5029,126.811),(634,'지자체/시흥시','신현동주말농장',9,'경기도',114,'시흥시','시흥시 포동 14','',37.4058,126.784),(635,'지자체/시흥시','대야동주말농장',9,'경기도',114,'시흥시','시흥시 계수동 425','',37.4557,126.8),(636,'지자체/시흥시','신천동주말농장',9,'경기도',114,'시흥시','시흥시 신천동 192','',37.4354,126.785),(637,'지자체/시흥시','능곡동주말농장',9,'경기도',114,'시흥시','시흥시 능곡동 707,708번지','',37.354,126.791),(638,'지자체/시흥시','방산동주말농장',9,'경기도',114,'시흥시','시흥시 방산동 산37-10번지','',37.4165,126.771),(639,'지자체/시흥시','정왕동주말농장',9,'경기도',114,'시흥시','시흥시 정왕동871-12','',37.36,126.742),(640,'지자체/시흥시','정왕동주말농장',9,'경기도',114,'시흥시','시흥시 정왕동 3-3','',37.3701,126.759),(641,'지자체/시흥시','월곶동주말농장',9,'경기도',114,'시흥시','시흥시 월곶동 425-4','',37.3764,126.761),(642,'개인','방아깨비텃밭',9,'경기도',94,'고양시 일산동구','고양시 일산동구 성석동 1300,1304','',37.6982,126.788),(643,'개인','에덴정원',9,'경기도',93,'고양시 덕양구','고양시 덕양구 고양동 430-1','',37.7105,126.898),(644,'개인','초록이네',9,'경기도',93,'고양시 덕양구','고양시 덕양구 용두동 544','',37.6394,126.88),(645,'개인','햇님과달님',9,'경기도',95,'고양시 일산서구','고양시 일산서구 법곳동 1541','',37.6663,126.716),(646,'개인','도촌주말농장',9,'경기도',94,'고양시 일산동구','고양시 일산동구 산황동 844','',37.6557,126.806),(647,'개인','원당텃밭',9,'경기도',93,'고양시 덕양구','고양시 덕양구 성사동 471','',37.658,126.846),(648,'개인','찬우물농장',9,'경기도',93,'고양시 덕양구','고양시 덕양구 대장동 563-6','',37.6332,126.824),(649,'개인','원빈주말농장',9,'경기도',93,'고양시 덕양구','고양시 덕양구 도내동 822-4','',37.6256,126.854),(650,'개인','동민주말농장',9,'경기도',94,'고양시 일산동구','고양시 일산동구 지영동 504','',37.7281,126.83),(651,'개인','원당역 주말농장',9,'경기도',93,'고양시 덕양구','고양시 덕양구  성사1동','',37.6565,126.839),(652,'개인','고양동 살림꾼 주말농장',9,'경기도',93,'고양시 덕양구','고양시 덕양구 벽제동 103 ','',37.7164,126.911),(653,'개인','선유동주말농장',9,'경기도',93,'고양시 덕양구','고양시 덕양구 선유동 103-11','',37.6944,126.915),(654,'개인','돌풍주말농장',9,'경기도',95,'고양시 일산서구','고양시 일산서구 덕이동 551','',37.6951,126.746),(655,'개인',' 하나로주말농장',9,'경기도',93,'고양시 덕양구','고양시 덕양구 대장동 567-3','',37.6332,126.815),(656,'지자체/시흥시','정왕본동주말농장',9,'경기도',114,'시흥시','시흥시 정왕동 24번지','',37.3818,126.842),(657,'지자체/시흥시','정왕본동주말농장',9,'경기도',114,'시흥시','시흥시 정왕동 855번지','',37.358,126.739),(658,'지자체/시흥시','거모동주말농장',9,'경기도',114,'시흥시','시흥시 거모동 1282-5','',37.3423,126.777),(659,'지자체/시흥시','연성동주말농장',9,'경기도',114,'시흥시','시흥시 신천동 650','',37.4345,126.773),(660,'지자체/시흥시','월곶동주말농장',9,'경기도',114,'시흥시','시흥시 월곶중앙로 30번길 7','',37.3888,126.742),(661,'지자체/안산시청','단원농장',9,'경기도',115,'안산시 단원구','안산시 단원구 초지동 747','',37.3135,126.731),(662,'지자체/안산시청','신길농장',9,'경기도',115,'안산시 단원구','안산시 신길동 63','',37.3388,126.779),(663,'지자체/성남시','성남시민농원 실버텃밭',9,'경기도',109,'성남시 중원구','성남시 중원구 성남동 4827번지','',37.4257,127.125),(665,'지자체/불현동주민자치위원회','우리가족 주말농장',9,'경기도',103,'동두천시','동두천시 탑동동 351-3','',37.8909,127.11),(666,'개인','고산농장',9,'경기도',126,'의정부시','의정부시 고산동 구성말','',37.7223,127.095),(667,'개인','신산농장',9,'경기도',126,'의정부시','의정부시 용현동 성골','',37.7339,127.08),(668,'개인','은지주말농장',9,'경기도',93,'고양시 덕양구','고양시 덕양구 도내동 835','',37.6273,126.857),(669,'개인','하나주말농장',9,'경기도',93,'고양시 덕양구','고양시 덕양구 원당동 497','',37.6767,126.856),(670,'개인','우보교육농장',9,'경기도',93,'고양시 덕양구','고양시 덕양구 벽제동 219-1','',37.7013,126.906),(671,'개인','늘푸른주말농장',9,'경기도',95,'고양시 일산서구','고양시 일산서구 구산동 1867','',37.6725,126.691),(672,'개인','청송주말농장',9,'경기도',95,'고양시 일산서구','고양시 일산서구 법곳동 1567','',37.664,126.716),(673,'개인','곡산주말농장',9,'경기도',93,'고양시 덕양구','고양시 덕양구 내곡동 134-3/4','',37.642,126.804),(674,'개인','도토리주말농장',9,'경기도',93,'고양시 덕양구','고양시 덕양구 원흥동 74','',37.6541,126.878),(675,'개인','둥지주말농장',9,'경기도',95,'고양시 일산서구','고양시 일산서구 덕이동 538','',37.6913,126.745),(676,'개인','대곡텃밭',9,'경기도',93,'고양시 덕양구','고양시 덕양구 내곡동 104-2','',37.6389,126.81),(677,'지자체/강동구청','길동텃밭',1,'서울특별시',2,'강동구','강동구 길동 36-2','',37.5411,127.151),(678,'지자체/강동구청','양지텃밭',1,'서울특별시',2,'강동구','강동구 암사동 195 외 1','',37.5573,127.131),(679,'지자체/강동구청','상일테마텃밭',1,'서울특별시',2,'강동구','강동구 상일동432-1외2','',37.5461,127.173),(680,'지자체/강동구청','선사테마텃밭',1,'서울특별시',2,'강동구','강동구 암사동176-1','',37.5584,127.128),(681,'지자체/강동구청','역사생태공원텃밭',1,'서울특별시',2,'강동구','강동구 암사동253-3외3','',37.5575,127.134),(683,'개인','관양주말농장',9,'경기도',118,'안양시 동안구','안양시 관양1동 515-1','',37.4113,126.962),(685,'지자체/동구청','대전시 공동체텃밭',6,'대전광역시',66,'동구','대전시 동구 세천동 285','',36.3327,127.489),(686,'지자체/동구청','대전시 공동체텃밭',6,'대전광역시',66,'동구','대전시 동구 세천동 246','',36.3319,127.492),(687,'지자체/동구청','대전시 공동체텃밭',6,'대전광역시',66,'동구','대전시 동구 세천동 211-1','',36.3363,127.488),(688,'지자체/동구청','대전시 공동체텃밭',6,'대전광역시',66,'동구','대전시 동구 세천동 26-8','',36.3392,127.492),(689,'지자체/동구청','대전시 공동체텃밭',6,'대전광역시',66,'동구','대전시 동구 신상동 218','',36.3462,127.498),(690,'지자체/동구청','대전시 공동체텃밭',6,'대전광역시',66,'동구','대전시 동구 이사동 92, 93','',36.2782,127.444),(694,'지자체/서구청','대전시 공동체텃밭',6,'대전광역시',67,'서구','대전시 서구 도안동 1366','',36.3177,127.347),(698,'지자체/유성구청','대전시 공동체텃밭',6,'대전광역시',68,'유성구','대전시 유성구 죽동 228-6','',36.3777,127.332),(707,'지자체/대덕구청','대전시 공동체텃밭',6,'대전광역시',65,'대덕구','대전시 대덕구 읍내동 356-21, 357-4','',36.374,127.419),(711,'지자체/대덕구청','대전시 공동체텃밭',6,'대전광역시',65,'대덕구','대전시 대덕구 비래동 산33-2','',36.3563,127.463);
/*!40000 ALTER TABLE `farm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite`
--

DROP TABLE IF EXISTS `favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite` (
  `favorite_id` int NOT NULL AUTO_INCREMENT,
  `ex_article_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`favorite_id`),
  KEY `ex_article_id` (`ex_article_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `favorite_ibfk_1` FOREIGN KEY (`ex_article_id`) REFERENCES `ex_article` (`ex_article_id`),
  CONSTRAINT `favorite_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite`
--

LOCK TABLES `favorite` WRITE;
/*!40000 ALTER TABLE `favorite` DISABLE KEYS */;
INSERT INTO `favorite` VALUES (5,6,3),(6,2,3),(8,6,4),(14,1,2),(16,12,2),(21,17,3),(36,17,2),(40,1,1),(43,2,1),(57,12,13),(58,6,13),(59,6,8),(60,12,8),(61,3,8),(62,12,1);
/*!40000 ALTER TABLE `favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `img_store`
--

DROP TABLE IF EXISTS `img_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `img_store` (
  `img_store_id` int NOT NULL AUTO_INCREMENT,
  `article_id` int DEFAULT NULL,
  `diary_id` int DEFAULT NULL,
  `ex_article_id` int DEFAULT NULL,
  `img_store_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`img_store_id`),
  KEY `diary_id` (`diary_id`),
  KEY `ex_article_id` (`ex_article_id`),
  CONSTRAINT `img_store_ibfk_1` FOREIGN KEY (`diary_id`) REFERENCES `diary` (`diary_id`),
  CONSTRAINT `img_store_ibfk_2` FOREIGN KEY (`ex_article_id`) REFERENCES `ex_article` (`ex_article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `img_store`
--

LOCK TABLES `img_store` WRITE;
/*!40000 ALTER TABLE `img_store` DISABLE KEYS */;
INSERT INTO `img_store` VALUES (1,NULL,NULL,1,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/f21426d0-d9e9-4b96-b182-92a5cce0e17a.jpg'),(2,NULL,NULL,2,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/067e71be-2540-4470-9f92-ce2db90386b4.png'),(3,NULL,NULL,3,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/fc40d949-0d55-4892-9196-39b051eb1d59.png'),(5,NULL,2,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/7fda3018-effd-4388-b4c2-c68fa20abbdc.png'),(6,NULL,NULL,4,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/b5fb8021-a4e5-40b7-9ff2-7085b5f61ccc.jpg'),(7,NULL,3,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/00ce0c1e-5a00-4215-97dc-f3ab224cd18d.jpg'),(8,NULL,3,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/d96c841d-c093-4dcc-ab86-54da2f2a5475.jpg'),(9,NULL,3,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/f3463ad2-42da-4a3e-a715-741a84fa76fa.jpg'),(10,NULL,3,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/0a096728-aa4a-4437-816e-1994fc0292e6.jpg'),(11,NULL,3,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/65437ced-c4bb-4cf1-8694-70e5033c16dd.jpg'),(12,NULL,4,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/4fe9a4de-587c-4204-8452-647e3f553e8f.jpg'),(13,NULL,4,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/58d5d97e-e6e0-479c-9776-73539389e052.jpg'),(14,NULL,4,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/282f90dd-b9e3-4025-a15d-30f77a8bcdf7.jpg'),(15,NULL,4,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/e1be0707-3a84-476d-b724-5da0fd497395.jpg'),(16,NULL,4,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/6ec55006-e23e-4972-b592-9a93a92ed42f.jpg'),(18,NULL,NULL,6,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/539bf86d-9d7b-4c28-a6bc-e2bd135f1405.jpg'),(19,NULL,5,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/e839bd1a-8383-469e-9afe-8df064fa891b.jpg'),(20,NULL,5,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/1f2b34e8-4afb-43db-bfbf-c8a89031b468.jpg'),(21,NULL,5,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/57a27873-750c-4f44-bbcb-7a5de05cd36d.jpg'),(22,NULL,5,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/a68eff1b-d5c5-4bfd-bb8a-16d7c5c8a125.jpg'),(23,NULL,5,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/a507239c-cc75-426c-a343-3a076a5c8a4d.jpg'),(25,NULL,NULL,8,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/33a49910-7149-40a6-9b9d-af1e97fc200a.png'),(26,1,NULL,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/d307a5f2-c4d0-4c95-8dc0-17c78ad3a2d8.jpeg'),(27,2,NULL,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/74f7c967-4211-4241-97a8-aeaf172d9e2f.png'),(28,3,NULL,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/8e989535-12a8-47ed-8f13-e41e0b120c76.jpg'),(29,NULL,NULL,9,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/61e20ec8-0b0a-44c5-ba7f-f05ca0e27022.png'),(30,4,NULL,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/63d3ef28-e59d-4d90-8923-83754b1083a6.png'),(31,NULL,6,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/b2a5fb04-c38d-4508-b99e-fa2825b66392.jpg'),(39,NULL,NULL,12,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/27f5c12c-1f0b-418a-a8ce-ba3b6caca5b1.jpg'),(51,NULL,NULL,17,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/47df7c05-dd12-4cb8-9050-fa2c09ce966d.jpg'),(79,NULL,24,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/21498fb2-e955-4797-8cad-d2966ef81f38.jpg'),(83,14,NULL,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/7763ec26-2eb6-4657-bca2-e6d7d8eae734.jpg'),(84,14,NULL,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/8466ea98-067e-4955-acc4-c2f2168400ff.jpg'),(85,14,NULL,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/860ac9cc-1c4c-4e02-8fea-e4a8cc47898a.jpg'),(89,NULL,28,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/5884ba8e-7213-45f6-b29d-0006c65184b0.png'),(90,NULL,28,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/4db5e586-62af-40d2-b083-de7729eb5743.jpg'),(92,NULL,30,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/1eea0e3a-1013-460f-afbb-58dc81778d13.jpg'),(93,NULL,31,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/158aafc6-ef26-4bfc-9442-1b86606d5156.jpg'),(94,NULL,31,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/25715abe-4d4b-4bd3-8b83-b5c0f40264ec.jpg'),(98,NULL,39,NULL,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/3ba62c23-6b94-4e3f-bc5c-096a73b0bfb2.jpg'),(101,NULL,NULL,38,'https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/15d2e72a-ad2b-4290-80de-fb6a7470abb6.jpg');
/*!40000 ALTER TABLE `img_store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `love`
--

DROP TABLE IF EXISTS `love`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `love` (
  `love_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `article_id` int NOT NULL,
  PRIMARY KEY (`love_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `love`
--

LOCK TABLES `love` WRITE;
/*!40000 ALTER TABLE `love` DISABLE KEYS */;
INSERT INTO `love` VALUES (1,2,1),(2,1,2),(3,3,1),(5,3,3),(7,2,4),(8,3,10),(9,2,11),(11,1,13),(13,1,12),(14,9,12),(16,1,14);
/*!40000 ALTER TABLE `love` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `my_badge`
--

DROP TABLE IF EXISTS `my_badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_badge` (
  `my_badge_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `badge_id` int NOT NULL,
  PRIMARY KEY (`my_badge_id`),
  KEY `user_id` (`user_id`),
  KEY `badge_id` (`badge_id`),
  CONSTRAINT `my_badge_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `my_badge_ibfk_2` FOREIGN KEY (`badge_id`) REFERENCES `badge` (`badge_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_badge`
--

LOCK TABLES `my_badge` WRITE;
/*!40000 ALTER TABLE `my_badge` DISABLE KEYS */;
INSERT INTO `my_badge` VALUES (2,3,3),(4,3,4),(5,10,3),(7,2,3),(8,13,3),(9,1,3),(10,4,3);
/*!40000 ALTER TABLE `my_badge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pack_diary`
--

DROP TABLE IF EXISTS `pack_diary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pack_diary` (
  `pack_diary_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `crop_type_id` int NOT NULL,
  `pack_diary_title` varchar(30) NOT NULL,
  `pack_diary_cul_start_at` date NOT NULL,
  `pack_diary_cul_end_at` date DEFAULT NULL,
  `pack_diary_growth_step` int NOT NULL DEFAULT '0',
  `pack_diary_created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`pack_diary_id`),
  KEY `user_id` (`user_id`),
  KEY `crop_type_id` (`crop_type_id`),
  CONSTRAINT `pack_diary_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `pack_diary_ibfk_2` FOREIGN KEY (`crop_type_id`) REFERENCES `crop_type` (`crop_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pack_diary`
--

LOCK TABLES `pack_diary` WRITE;
/*!40000 ALTER TABLE `pack_diary` DISABLE KEYS */;
INSERT INTO `pack_diary` VALUES (2,4,5,'가나다라마바상추','2024-04-03',NULL,0,'2024-04-03 02:08:09'),(3,3,1,'쑥쑥 무순','2024-03-30','2024-04-03',3,'2024-04-03 02:08:24'),(4,3,3,'멋쟁이토마토','2024-04-01',NULL,4,'2024-04-03 02:50:14'),(5,3,5,'상츄츄','2024-03-31',NULL,3,'2024-04-03 09:08:12'),(6,7,3,'톰아토','2024-03-27',NULL,0,'2024-04-03 09:20:03'),(7,3,1,'test','2024-04-03','2024-04-03',3,'2024-04-03 09:38:34'),(8,10,1,'test','2024-04-03',NULL,3,'2024-04-03 09:41:10'),(9,2,1,'무순이','2024-04-01','2024-04-03',3,'2024-04-03 09:41:38'),(11,8,1,'무수니','2024-04-03',NULL,0,'2024-04-03 09:42:50'),(26,3,1,'test','2024-04-03','2024-04-03',3,'2024-04-03 10:06:28'),(35,9,1,'무수니','2024-04-03',NULL,0,'2024-04-03 12:11:46'),(37,13,1,'무수니','2024-04-03',NULL,3,'2024-04-03 15:22:49'),(39,1,5,'상츄','2024-03-22',NULL,0,'2024-04-03 15:58:50'),(40,1,1,'무수니','2024-03-29','2024-04-03',3,'2024-04-03 15:59:17'),(41,4,1,'SSAFY','2024-04-03',NULL,3,'2024-04-03 16:35:11'),(43,1,3,'주스될거야','2024-03-22',NULL,0,'2024-04-03 17:56:17');
/*!40000 ALTER TABLE `pack_diary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trans`
--

DROP TABLE IF EXISTS `trans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trans` (
  `trans_id` int NOT NULL AUTO_INCREMENT,
  `ex_article_id` int NOT NULL,
  `trans_sell_price` int NOT NULL,
  PRIMARY KEY (`trans_id`),
  KEY `ex_article_id` (`ex_article_id`),
  CONSTRAINT `trans_ibfk_1` FOREIGN KEY (`ex_article_id`) REFERENCES `ex_article` (`ex_article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trans`
--

LOCK TABLES `trans` WRITE;
/*!40000 ALTER TABLE `trans` DISABLE KEYS */;
INSERT INTO `trans` VALUES (1,2,1400),(2,3,10000),(3,6,0),(4,9,0),(7,17,10000);
/*!40000 ALTER TABLE `trans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_email` varchar(50) NOT NULL,
  `user_pwd` varchar(255) NOT NULL,
  `user_role` varchar(20) NOT NULL,
  `user_nick` varchar(30) DEFAULT NULL,
  `user_thumb` varchar(255) DEFAULT NULL,
  `user_rank` varchar(20) DEFAULT NULL,
  `user_location` varchar(20) DEFAULT NULL,
  `user_provider` varchar(20) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test1@test.com','$2a$10$peb36k9fdA2DP4fwafahh.qEMTJZV3HuXN9KElF/Mhhvqc7.OgQwG','ROLE_MEMBER','수근농부','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/5d69bde7-bed3-4abe-9a5c-b7119b8548a9.jpg','새싹','광주 광산구 오선동','MYAPP'),(2,'test2@test.com','$2a$10$M1rATcPDuEw1vIRQHDSvYueDOUfQmgshPK2gD1lgiYXGcB9h.dqZi','ROLE_MEMBER','여름쿨톤','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/5c269c28-5b22-496f-a6c6-0472e8b189ae.jpg','새싹','광주 광산구 오선동','MYAPP'),(3,'test3@test.com','$2a$10$WwQlzmUtnyNgvZAy.6yS1ea3bzWITJbXb/zPLPEQ/MsqObmOMYrC2','ROLE_MEMBER','김민지','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/eaf07255-15b6-4a16-8911-38d1442b0139.png','새싹','광주 광산구 오선동','MYAPP'),(4,'test4@test.com','$2a$10$.wjllfiZbgJ1hpjOkqCcROunhTQegchGba9UQ9hFMwmoBpOQJne8q','ROLE_MEMBER','오해원','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/85d7b0ad-b5c4-4cbb-8c57-ec0f3ff7bf07.png','새싹','광주 광산구 오선동','MYAPP'),(5,'dhwjdals1324@gmail.com','DummyPasswordeoar!@3','ROLE_MEMBER','오정민-google','https://lh3.googleusercontent.com/a/ACg8ocL7M4jBcWNqjstoK9SBBpqPXrLpbDp9kqSWIYS2k5te=s96-c','새싹','광주 광산구 오선동','GOOGLE'),(6,'test10@test.com','$2a$10$OWVePErrA0sNWKfo4KYqA.N2UgVOYAzkDTla449lJ4HbUkh2HFA.6','ROLE_MEMBER','로이킴','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/default_thumnail.svg','새싹',NULL,'MYAPP'),(7,'test11@test.com','$2a$10$NOv48WXN0U968j7Uiij8cefaQEhtSMEdEW5OC8tuPtWVvRhuTkUci','ROLE_MEMBER','해린','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/19c1a7e3-200a-4b39-a53d-560199124fb2.png','새싹','광주 광산구 오선동','MYAPP'),(8,'test12@test.com','$2a$10$9jYiZ2bZcafd1qtkjcyht.ahI99g1.bp2Z//2BLVJSo5p.M29qi4S','ROLE_MEMBER','이혜인','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/43f0896d-f23b-425d-93b1-7f01935bcb1d.jpg','새싹','광주 광산구 오선동','MYAPP'),(9,'jmsavemail@gmail.com','DummyPasswordeoar!@3','ROLE_MEMBER','황솔촌','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/320c23d7-46fe-49cb-bbf1-bdf851375e60.jpg','새싹','광주 광산구 오선동','GOOGLE'),(10,'leeminkyu1212@gmail.com','DummyPasswordeoar!@3','ROLE_MEMBER','이민규-google','https://lh3.googleusercontent.com/a/ACg8ocIZLeZQ1kKvP9NmSd9AajMIl_DXSd8yHzbpLhoNjFBJ=s96-c','새싹',NULL,'GOOGLE'),(11,'dntjr5741@gmail.com','DummyPasswordeoar!@3','ROLE_MEMBER','심우석-google','https://lh3.googleusercontent.com/a/ACg8ocIy0O9WiDIQgI7cOvzZ8fPLivDd-06VH1kWpzHsy4ZJ=s96-c','새싹',NULL,'GOOGLE'),(13,'test13@test.com','$2a$10$phPGbH2gs9eB582Kfn/WU.viDsFfYaTqw3amVDseDptqx8jx8beSu','ROLE_MEMBER','고두심','https://fullerting-s3-v2.s3.ap-northeast-2.amazonaws.com/b48ba3a9-2e50-47fc-97ef-867f1b3f186c.jpg','새싹','광주 광산구 오선동','MYAPP'),(14,'donggeun3484@gmail.com','DummyPasswordeoar!@3','ROLE_MEMBER','안녕씅ㅇㅇㅇㅇㅇㅇ','https://lh3.googleusercontent.com/a/ACg8ocJADhGC86ENFQ_UCtw4UwPmgN1R4CwJnQTnvUA0vXdZ=s96-c','새싹','광주 광산구 오선동','GOOGLE');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-04  9:12:28
