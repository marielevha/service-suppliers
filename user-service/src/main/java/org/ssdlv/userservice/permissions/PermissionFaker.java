package org.ssdlv.userservice.permissions;

public class PermissionFaker {

    /**
     * Méthodes permettant d'enregistrer des permissions lors du lancement du projet
     * @param permissionRepository
     */
    public static void permissionFaker(PermissionRepository permissionRepository) {
        permissionRepository.save(new Permission("CATEGORY:READ"));
        permissionRepository.save(new Permission("CATEGORY:UPDATE"));
        permissionRepository.save(new Permission("CATEGORY:DELETE"));
        permissionRepository.save(new Permission("CATEGORY:CREATE"));

        permissionRepository.save(new Permission("TAG:READ"));
        permissionRepository.save(new Permission("TAG:UPDATE"));
        permissionRepository.save(new Permission("TAG:DELETE"));
        permissionRepository.save(new Permission("TAG:CREATE"));

        permissionRepository.save(new Permission("JOB:READ"));
        permissionRepository.save(new Permission("JOB:UPDATE"));
        permissionRepository.save(new Permission("JOB:DELETE"));
        permissionRepository.save(new Permission("JOB:CREATE"));

        permissionRepository.save(new Permission("OFFER:READ"));
        permissionRepository.save(new Permission("OFFER:UPDATE"));
        permissionRepository.save(new Permission("OFFER:DELETE"));
        permissionRepository.save(new Permission("OFFER:CREATE"));

        permissionRepository.save(new Permission("USER:READ"));
        permissionRepository.save(new Permission("USER:UPDATE"));
        permissionRepository.save(new Permission("USER:DELETE"));
        permissionRepository.save(new Permission("USER:CREATE"));

        permissionRepository.save(new Permission("USER-PERMISSION:GRANT"));
        permissionRepository.save(new Permission("USER-PERMISSION:REVOKE"));
    }
}
