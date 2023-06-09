package com.projet.complexe_sportif.aspect;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.projet.complexe_sportif.controller..*(..))")
    public void logPointcut() {
    }

    @Before("logPointcut()") // message pour indiquer les méthodes appelées
    public void methodeAppelee(JoinPoint joinPoint) {
        System.out.println("\u001B[33m ~~~~~~~~~~~~~~~~ Méthode appelée: " + joinPoint.getSignature().toShortString()
                + " ~~~~~~~~~~~~~~~~ \033[0m");
    }

    @AfterReturning(pointcut = "logPointcut()", returning = "result") // message pour indiquer les méthodes réussites
    public void methodeReussie(JoinPoint joinPoint, Object result) {
        System.out.println("\033[32m ~~~~~~~~~~~~~~~~ Méthode " + joinPoint.getSignature().toShortString()
                + " terminée avec succès. ~~~~~~~~~~~~~~~~ \033[0m");
    }

    @AfterThrowing(pointcut = "logPointcut()", throwing = "exception") // message pour indiquer les méthodes échouées
    public void methodeEchouee(JoinPoint joinPoint, Throwable exception) {
        System.out.println("\033[31m ~~~~~~~~~~~~~~~~ Erreur lors de l'exécution de la méthode "
                + joinPoint.getSignature().toShortString() + " : " + exception.getMessage()
                + " ~~~~~~~~~~~~~~~~ \033[0m");
    }

    @After("execution(* javax.persistence.EntityManager.persist(..))") // message indiquant qu'une donnée a été ajoutée à la bdd
    public void insertIntoAlerte() {
        System.out.println("\u001B[35m /!\\ /!\\ /!\\ ATTENTION BDD: Un nouvel enregistrement a été ajouté à la base de données  /!\\ /!\\ /!\\\u001B[0m");
    }

    @After("execution(* javax.persistence.EntityManager.remove(..))") // message indiquant qu'une donnée a été supprimée de la bdd
    public void deleteFromAlerte() {
        System.out.println("\u001B[35m /!\\ /!\\ /!\\ ATTENTION BDD: Un enregistrement a été supprimé de la base de données /!\\ /!\\ /!\\\u001B[0m");
    }

    @After("execution(* javax.persistence.EntityManager.merge(..))") // message indiquant qu'une donnée a été modifiée dans la bdd
    public void updateAlerte() {
        System.out.println("\u001B[35m /!\\ /!\\ /!\\ ATTENTION BDD: Un enregistrement a été modifié dans la base de données /!\\ /!\\ /!\\\u001B[0m");
    }

    //méthode qui envoit un message lors d'un problème dans une requête sur la bdd
    @AfterThrowing(pointcut = "execution(* javax.persistence.EntityManager.persist(..)) || execution(* javax.persistence.EntityManager.remove(..)) || execution(* javax.persistence.EntityManager.merge(..))", throwing = "exception")
    public void bddAlerteEchec(JoinPoint joinPoint, Throwable exception) {
        System.out.println("\u001B[31m ~~~~~~~~~~~~~~~~ Erreur lors de l'exécution de la méthode "
                + joinPoint.getSignature().toShortString() + " : " + exception.getMessage()
                + " ~~~~~~~~~~~~~~~~ \u001B[0m");
    }
    

    /*
     * private static boolean lancee = false;
     * 
     * @After("execution(* org.springframework.boot.SpringApplication.run(..))")
     * public void lancementReussi() {
     * if (!lancee) {
     * System.out.println(
     * "\033[32m.---------------------------------------------------------------.\033[0m"
     * );
     * System.out.
     * println("\033[32m|                                                               |\033[0m"
     * );
     * System.out.
     * println("\033[32m|                 Application lancée avec succès                |\033[0m"
     * );
     * System.out.
     * println("\033[32m|                                                               |\033[0m"
     * );
     * System.out.println(
     * "\033[32m'---------------------------------------------------------------'\033[0m"
     * );
     * lancee = true;
     * }
     * }
     * 
     * @AfterThrowing(
     * pointcut="execution(* org.springframework.boot.SpringApplication.run(..))",
     * throwing="ex")
     * public void lancementEchoue(Throwable ex) {
     * if (!lancee) {
     * System.out.println(
     * "\033[31m.---------------------------------------------------------------.\033[0m"
     * );
     * System.out.
     * println("\033[31m|                                                               |\033[0m"
     * );
     * System.out.
     * println("\033[31m|              Le lancement de l'application a échou            |\033[0m"
     * );
     * System.out.
     * println("\033[31m|                                                               |\033[0m"
     * );
     * System.out.println("\033[31m|                       " + ex.getMessage() +
     * "                 |\033[0m");
     * System.out.
     * println("\033[31m|                                                               |\033[0m"
     * );
     * System.out.println(
     * "\033[31m'---------------------------------------------------------------'\033[0m"
     * );
     * lancee = true;
     * }
     * }
     */

}
