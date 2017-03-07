CREATE SCHEMA `sys_food` ;

use sys_food

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cpf` varchar(14) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `endereco` varchar(255) DEFAULT NULL,
  `nome` varchar(80) NOT NULL,
  `perfil_usuario` varchar(13) NOT NULL,
  `senha` varchar(64) NOT NULL,
  `telefone` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_898atepo5gx8dqj60c07k766b` (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `sys_food`.`usuario`
(`cpf`,
`email`,
`endereco`,
`nome`,
`perfil_usuario`,
`senha`,
`telefone`)
VALUES
('448.484.101-00',
'teste@gmail.com',
'Rua X e Y',
'Administrador',
'ADMINISTRADOR',
'$2a$10$UWTwjjoieh0FkNm/toAw5.RRG3rIudNbZz2klO/54ziLhVO0iroiK',
'(99)9 9999-9999');