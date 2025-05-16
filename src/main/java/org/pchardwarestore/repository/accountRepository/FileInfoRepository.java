package org.pchardwarestore.repository.accountRepository;

import org.pchardwarestore.entity.accountEntity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

}
