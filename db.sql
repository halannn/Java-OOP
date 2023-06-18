CREATE DATABASE pbo_database;

-- *Perusahaan*

CREATE TABLE `pbo_database`.`perusahaan` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nama_perusahaan` VARCHAR(255) NOT NULL,
  `no_telp` VARCHAR(20) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `alamat` TEXT(255) NOT NULL,
  PRIMARY KEY (`id`));

-- *role*

CREATE TABLE `pbo_database`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_own_perusahaan` INT NOT NULL,
  `nama_role` VARCHAR(255) NOT NULL,
  `permissions_flag` INT NOT NULL,
  PRIMARY KEY (`id`),
INDEX  (`id_own_perusahaan`));

ALTER TABLE `pbo_database`.`role`
ADD CONSTRAINT `fk_role_perusahaan`
FOREIGN KEY (`id_own_perusahaan`)
REFERENCES `pbo_database`.`perusahaan`(`id`) 
ON DELETE CASCADE 
ON UPDATE CASCADE;

-- *user*

CREATE TABLE `pbo_database`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` TEXT NOT NULL,
  `id_own_perusahaan` INT NOT NULL,
  `id_role` INT NOT NULL,
  `status` ENUM('Aktif', 'Nonaktif') NOT NULL,
  PRIMARY KEY (`id`),
INDEX (`id_role`),
INDEX (`id_own_perusahaan`));

ALTER TABLE `pbo_database`.`user`
ADD CONSTRAINT `fk_user_perusahaan`
FOREIGN KEY (`id_own_perusahaan`)
REFERENCES `pbo_database`.`perusahaan`(`id`) 
ON DELETE CASCADE 
ON UPDATE CASCADE;

ALTER TABLE `pbo_database`.`user`
ADD CONSTRAINT `fk_user_role`
FOREIGN KEY (`id_role`)
REFERENCES `pbo_database`.`role`(`id`) 
ON DELETE CASCADE 
ON UPDATE CASCADE;

-- *akun*

CREATE TABLE `pbo_database`.`akun` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_own_perusahaan` INT NOT NULL,
  `nama_akun` VARCHAR(255) NOT NULL,
  `jenis_akun` ENUM('aset', 'modal', 'pengeluaran', 'pendapatan', 'hutang', 'piutang') NOT NULL,
  PRIMARY KEY (`id`),
INDEX (`id_own_perusahaan`));

ALTER TABLE `pbo_database`.`akun`
ADD CONSTRAINT `fk_akun_perusahaan`
FOREIGN KEY (`id_own_perusahaan`)
REFERENCES `pbo_database`.`perusahaan`(`id`) 
ON DELETE CASCADE 
ON UPDATE CASCADE;

-- *klien*

CREATE TABLE `pbo_database`.`klien` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_own_perusahaan` INT NOT NULL,
  `no_telp` VARCHAR(20) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `perusahaan_asal` VARCHAR(255) NOT NULL,
  `alamat` TEXT NOT NULL,
  PRIMARY KEY (`id`),
INDEX (`id_own_perusahaan`));

ALTER TABLE `pbo_database`.`klien`
ADD CONSTRAINT `fk_klien_perusahaan`
FOREIGN KEY (`id_own_perusahaan`)
REFERENCES `pbo_database`.`perusahaan`(`id`) 
ON DELETE CASCADE 
ON UPDATE CASCADE;

-- *transaksi*

CREATE TABLE `pbo_database`.`transaksi` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_own_perusahaan` INT NOT NULL,
  `tanggal` DATE NOT NULL,
  `id_akun` INT NOT NULL,
  `posisi_akun` ENUM('debit', 'kredit') NOT NULL,
  `id_klien` INT NOT NULL,
  `keterangan` TEXT NOT NULL,
  `nominal` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
INDEX (`id_klien`),
INDEX (`id_akun`),
INDEX (`id_own_perusahaan`));

ALTER TABLE `pbo_database`.`transaksi`
ADD CONSTRAINT `fk_transaksi_perusahaan`
FOREIGN KEY (`id_own_perusahaan`)
REFERENCES `pbo_database`.`perusahaan`(`id`) 
ON DELETE CASCADE 
ON UPDATE CASCADE;

ALTER TABLE `pbo_database`.`transaksi`
ADD CONSTRAINT `fk_transaksi_akun`
FOREIGN KEY (`id_akun`)
REFERENCES `pbo_database`.`akun`(`id`) 
ON DELETE CASCADE 
ON UPDATE CASCADE;

ALTER TABLE `pbo_database`.`transaksi`
ADD CONSTRAINT `fk_transaksi_klien`
FOREIGN KEY (`id_klien`)
REFERENCES `pbo_database`.`klien`(`id`) 
ON DELETE CASCADE 
ON UPDATE CASCADE;