//var tab_verif=new Array;
//tab_verif["nom"]=0;
//tab_verif["prenon"]=0;
//tab_verif["adresse_1"]=0;
//tab_verif["email"]=0;
//tab_verif["fixe"]=1;
//tab_verif["portable"]=1;
//tab_verif["date_naissance"]=0;
//tab_verif["licence"]=1;
//tab_verif["mdp"]=0;
//tab_verif["email_parrain"]=1;
//tab_verif["adresse_1"]=0;

$(document).ready(
    function(){});
//        $("#Register\\:forename").blur(function() {
//            notEmpty("#Register\\:forename");
//        });
//        $("#Register\\:forename").keyup(function() {
//            verifRegexp("#Register\\:forename","^[a-zA-Z-]{1,32}$","*Must contain only letters and dash");
//        });
//        $("#Register\\:surname").blur(function() {
//            notEmpty("#Register\\:surname");
//        });
//        $("#Register\\:surname").keyup(function() {
//            verifRegexp("#Register\\:surname","^[a-zA-Z-]{1,32}$","*Must contain only letters and dash");
//        });
//        $("#Register\\:username").blur(function() {
//            notEmpty("#Register\\:username");
//        });
//        $("#Register\\:username").keyup(function() {
//            verifRegexp("#Register\\:username","^[a-zA-Z_]{1,32}$","*Must contain only letters and underscore");
//        });
//        $("#Register\\:password").blur(function() {
//            notEmpty("#Register\\:password");
//        });
//        $("#Register\\:password").keyup(function() {
//            verifConfirm();
//            verifRegexp("#Register\\:password","^(?=.*\\d).{6,32}$","*Must contain at least 6 characters including 1 figure");
//        });
//        $("#Register\\:confirmpassword").blur(function() {
//            notEmpty("#Register\\:confirmpassword");
//        });
//        $("#Register\\:confirmpassword").keyup(function() {
//            verifConfirm();
//        });
//        $("#Register\\:email").blur(function() {
//            notEmpty("#Register\\:email");
//        });
//        $("#Register\\:email").keyup(function() {
//            verifRegexp("#Register\\:email","^([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})$","*Must be valid");
//        });
//        $("#Register\\:organisationName").blur(function() {
//            notEmpty("#Register\\:organisationName");
//        });
////        $("#Register\\:organisationName").keyup(function() {
////            verifRegexp("#Register\\:organisationName","^[a-zA-Z0-9-]{1,32}$","*Must contain only letters,numbers and dash");
////        });
//        $("#Register\\:postalAddress").blur(function() {
//            notEmpty("#Register\\:postalAddress");
//        });
//        $("#Register\\:postalAddress").keyup(function() {
//            verifRegexp("#Register\\:postalAddress","^[a-zA-Z0-9- ]{1,32}$","*Must contain only letters,numbers,interspace and dash");
//        });
//        $("#Register\\:postCode").blur(function() {
//            notEmpty("#Register\\:postCode");
//        });
//        $("#Register\\:postCode").keyup(function() {
//            verifRegexp("#Register\\:postCode","^[a-zA-Z0-9]{1,16}$","*Must contain only letters and numbers");
//        });
//        $("#Register\\:organisationActivity").blur(function() {
//            notEmpty("#Register\\:organisationActivity");
//        });
//        $("#Register\\:organisationActivity").keyup(function() {
//            verifRegexp("#Register\\:organisationActivity","^[a-zA-Z0-9]{1,16}$","*Must contain only letters and numbers");
//        });
//        $("#Register\\:hemisNumber").blur(function() {
//            notEmpty("#Register\\:hemisNumber");
//        });
//        $("#Register\\:hemisNumber").keyup(function() {
//            verifRegexp("#Register\\:hemisNumber","^[a-zA-Z0-9]{1,16}$","*Must contain only letters and numbers");
//        });
//
//    /*
//		if($("#modif").get(0))
//		{
//			$(".btn_submit").removeAttr("disabled");
//			if(!($("#licence_checkbox").is(':checked')))
//			{
//				$("#no_licence").attr("disabled", true);
//				$("#federation").attr("disabled", true);
//
//			}
//			if(!($("#mon_parrain_checkbox").is(':checked')))
//				$("#email_parrain").attr("disabled", true);
//			if(!($("#nationalite_2_checkbox").is(':checked')))
//				$("#nationalite_2_adherent").attr("disabled", true);
//
//			$("#modif").submit(function(){
//				check_modif()
//				});
//		}
//		else
//		{
//			$(".btn_submit").attr("disabled","disabled");
//			$("#email_parrain").blur(function() {
//				if($("#email_parrain").val()!="")
//				appel_ajax($("#email_parrain").val(), 2);
//				else
//				$("span.error_email_parrain").css("color","black").text("*");
//					});
//			$("#adresse_1").keyup(function() {
//					verifier_adresse();
//			});
//			$("#mot_de_passe").keyup(function() {
//							verifier_mdp();
//							});
//			$("#mot_de_passe_verif").keyup(function() {
//							verifier_mdp2();
//							});
//			$("#email_verif").keyup(function() {
//							emails_identiques();
//							});
//            $("#nom_adherent").keyup(function() {
//                        	verifier_nom();
//                        	});
//			$("#prenom_adherent").keyup(function() {
//                        	verifier_prenom();
//                       		 });
//			$("#email_adherent").blur(function() {
//				appel_ajax($("#email_adherent").val(), 1)
//                       		 });
//			$("#telephone_fixe").keyup(function() {
//                        	verifier_fixe();
//                        	});
//			$("#telephone_portable").keyup(function() {
//                        	verifier_portable();
//                       		 });
//			$("#date_naissance_adherent").blur(function() {
//                        	verifier_date();
//                        	});
//			$("#no_licence").keyup(function() {
//				if($("#no_licence").val()!="")
//					verifier_licence();
//				else
//					$("span.error_licence").css("color","black").show().text("*");
//                        	});
//
//			$("#mon_parrain").blur(function() {
//                       		verifier_parrain();
//                      		});
//
//			$("#no_licence").attr("disabled", true);
//			$("#federation").attr("disabled", true);
//			$("#email_parrain").attr("disabled", true);
//			$("#nationalite_2_adherent").attr("disabled", true);
//			$("span.error_licence").hide();
//			$("span.error_email_parrain").hide();
//		}
//
//                */
//    });
//
//function notEmpty(tag){
//    if($(tag).val()=="")
//        $(tag+"Error").html("*Field required");
//    else
//        $(tag+"Error").html("");
//}
//function verifRegexp(tag,reg,mess){
//    //    if($(tag).val()=="")
//    //        $(tag+"Error").html("*Field required");
//    //    else
//    if($(tag).val().match(reg))
//        $(tag+"Error").html("");
//    else
//        $(tag+"Error").html(mess);
//}

//function verifConfirm(){
//    if($("#Register\\:confirmpassword").val() != $("#Register\\:password").val())
//        $("#Register\\:confirmpasswordError").html("Passwords must be the same");
//    else
//        $("#Register\\:confirmpasswordError").html("");
//}
//
//function appel_ajax(email, test)
//{
//    if(test==1)
//    {
//        $.ajax({
//            type: "POST",
//            url: "log/verif_email.php",
//            data: "email="+email,
//            success: function(msg){
//                email_unique(msg);
//            },
//            error: function(){
//                alert("Erreur serveur");
//            }
//        });
//    }
//    else if(test==2)
//    {
//
//        $.ajax({
//            type: "POST",
//            url: "log/verif_email.php",
//            data: "email="+email,
//            success: function(msg){
//                email_parrain(msg);
//            },
//            error: function(){
//                alert("Erreur serveur");
//            }
//        });
//    }
//
//}
//
//
//function verifier_adresse()
//{
//    if($("#adresse_1").val()=="")
//    {
//        $("span.error_adresse").html('<img src="Images/croix.png" height="15px">');
//        tab_verif["adresse_1"]=0;
//        check_tableau();
//    }
//    else
//    {
//        $("span.error_adresse").hide();
//        tab_verif["adresse_1"]=1;
//        check_tableau();
//    }
//
//}
//function email_parrain(msg)
//{
//    if(msg=='FAIL')
//    {
//        $("span.error_email_parrain").html('<img src="Images/check.png" height="15px">');
//        tab_verif["email_parrain"]=1;
//        check_tableau();
//    }
//    else
//    {
//        $("span.error_email_parrain").html('<img src="Images/croix.png" height="15px">');
//        tab_verif["email_parrain"]=0;
//        check_tableau();
//    }
//}
//
//function email_unique(msg)
//{
//    if(msg=='FAIL')
//    {
//        $("span.error_email").html('<img src="Images/croix.png" height="15px">');
//
//        tab_verif["email"]=0;
//        check_tableau();
//    }
//    else
//    {
//        verifier_email();
//        emails_identiques();
//    }
//}
//
//function verifier_mdp()
//{
//    var matchExp =/^[a-zA-Z0-9]{8,10}$/;
//    if($("#mot_de_passe").val().match(matchExp))
//    {
//        $("span.error_mdp").html('<img src="Images/check.png" height="15px">');
//        tab_verif["mdp"]=1;
//        verifier_mdp2();
//        check_tableau();
//    }
//    else
//    {
//        $("span.error_mdp").html('<img src="Images/croix.png" height="15px">');
//        tab_verif["mdp"]=0;
//        check_tableau();
//    }
//}
//
//function verifier_mdp2()
//{
//    if(($("#mot_de_passe").val()==$("#mot_de_passe_verif").val()))
//    {
//        $("span.error_mdp_verif").html('<img src="Images/check.png" height="15px">');
//        tab_verif["mdp"]=1;
//        check_tableau();
//    }
//    else
//    {
//        $("span.error_mdp_verif").html('<img src="Images/croix.png" height="15px">');
//        tab_verif["mdp"]=0;
//        check_tableau();
//    }
//}
//
//function emails_identiques()
//{
//    if($("#email_verif").val()==$("#email_adherent").val())
//    {
//        $("span.error_email_verif").html('<img src="Images/check.png" height="15px">');
//        tab_verif["email"]=1;
//        check_tableau();
//    }
//    else
//    {
//        $("span.error_email_verif").html('<img src="Images/croix.png" height="15px">');
//        tab_verif["email"]=0;
//        check_tableau();
//    }
//}
//
//function verifier_licence()
//{
//
//    var matchExp =/^[0-9]{2}\ {0,1}[0-9]{3}\ {0,1}[0-9]{6}\ {0,1}[a-zA-Z]{1}$/;
//
//
//    if($("#no_licence").val().match(matchExp))
//    {
//        $("span.error_licence").html('<img src="Images/check.png" height="15px">');
//        tab_verif["licence"]=1;
//        check_tableau();
//    }
//    else
//    {
//        $("span.error_licence").html('<img src="Images/croix.png" height="15px">');
//        tab_verif["licence"]=0;
//        check_tableau();
//    }
//}
//
//function nationalite_2()
//{
//    if($("#nationalite_2_checkbox").is(':checked'))
//        $("#nationalite_2_adherent").attr("disabled", "");
//    else
//        $("#nationalite_2_adherent").attr("disabled", true);
//}
//
//
//function licence()
//{
//    if($("#licence_checkbox").is(':checked'))
//    {
//        $("span.error_licence").show();
//        $("#no_licence").attr("disabled", "");
//        $("#federation").attr("disabled", "");
//
//        if($("#modif").get(0))
//            $("#no_licence").val(num_licence);
//
//        tab_verif["licence"]=0;
//
//        if($("#no_licence").val()!="")
//            verifier_licence();
//
//        check_tableau();
//    }
//    else
//    {
//        $("#no_licence").attr("disabled", true);
//        $("#federation").attr("disabled", true);
//        $("span.error_licence").hide();
//        tab_verif["licence"]=1;
//        check_tableau();
//    }
//}
//
//function parrain()
//{
//    if($("#mon_parrain_checkbox").is(':checked'))
//    {
//        $("#email_parrain").removeAttr("disabled");
//        $("span.error_email_parrain").show();
//        tab_verif["email_parrain"]=0;
//
//        if($("#email_parrain").val()!="")
//            appel_ajax2($("#email_parrain").val());
//
//        check_tableau();
//    }
//    else
//    {
//        $("#email_parrain").attr("disabled", true);
//        $("span.error_email_parrain").hide();
//        tab_verif["email_parrain"]=1;
//        check_tableau();
//    }
//}
//
//
//function verifier_date()
//{
//
//    var matchExp =/^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$/;
//
//    if($("#date_naissance_adherent").val().match(matchExp))
//    {
//        // On sépare la date en 3 variables pour vérification, parseInt() converti du texte en entier
//        var elem = $("#date_naissance_adherent").val().split('/');
//
//        j = elem[0];
//        m= elem[1];
//        a= elem[2];
//        var date = new Date();
//
//        // Définition du dernier jour de février
//        // Année bissextile si annnée divisible par 4 et que ce n'est pas un siècle, ou bien si divisible par 400
//        if (a%4 == 0 && a%100 !=0 || a%400 == 0) fev = 29;
//        else fev = 28;
//
//        // Nombre de jours pour chaque mois
//        var nbJours = new Array(31,fev,31,30,31,30,31,31,30,31,30,31);
//
//        if(m >= 1 && m <=12 && j >= 1 && j <= nbJours[m-1] && a < date.getFullYear() && a > 1900)
//        {
//            $("span.error_date_naissance").html('<img src="Images/check.png" height="15px">');
//            tab_verif["date_naissance"]=1;
//            check_tableau();
//        }
//
//        else
//        {
//            $("span.error_date_naissance").html('<img src="Images/croix.png" height="15px">');
//            tab_verif["date_naissance"]=0;
//            check_tableau();
//        }
//    }
//    else
//    {
//        $("span.error_date_naissance").html('<img src="Images/croix.png" height="15px">');
//        tab_verif["date_naissance"]=0;
//        check_tableau();
//    }
//
//}
//
//function verifier_fixe()
//{
//    var matchExp =/^[0-9]{10}$/;
//    if($('#telephone_fixe').val()!="")
//    {
//        if($("#telephone_fixe").val().match(matchExp)){
//            $("span.error_fixe").html('<img src="Images/check.png" height="15px">');
//            tab_verif["fixe"]=1;
//            check_tableau();
//        }
//        else{
//            $("span.error_fixe").html('<img src="Images/croix.png" height="15px">');
//            tab_verif["fixe"]=0;
//            check_tableau();
//        }
//    }
//    else
//    {
//        $("span.error_fixe").css("display","none");
//    }
//}
//
//
//function verifier_portable()
//{
//    var matchExp =/^[0-9]{10}$/;
//    if($('#telephone_portable').val()!="")
//    {
//        if($("#telephone_portable").val().match(matchExp))
//        {
//            $("span.error_portable").html('<img src="Images/check.png" height="15px">');
//            tab_verif["portable"]=1;
//            check_tableau();
//        }
//        else
//        {
//            $("span.error_portable").html('<img src="Images/croix.png" height="15px">');
//            tab_verif["portable"]=0;
//            check_tableau();
//        }
//    }
//    else
//        $("span.error_portable").css("display","none");
//
//}
//
//
//function verifier_email()
//{
//    var matchExp =/^[a-zA-Z0-9_.-]+@[a-z]{0,10}[.]{1}[a-z]{2,4}$/;
//    if($("#email_adherent").val().match(matchExp))
//    {
//        $("span.error_email").html('<img src="Images/check.png" height="15px">');
//        tab_verif["email"]=1;
//        check_tableau();
//    }
//    else
//    {
//        $("span.error_email").html('<img src="Images/croix.png" height="15px">');
//        tab_verif["email"]=0;
//        check_tableau();
//    }
//}
//
//
//
//function verifier_nom()
//{
//    var matchExp =  /^[a-zA-Z ]{2,}$/;
//    if($("#nom_adherent").val().match(matchExp))
//    {
//        $("span.error_nom").html('<img src="Images/check.png" height="15px">');
//        //$("span.error_nom").html('<img src="ok2.png" width=35>');
//        tab_verif["nom"]=1;
//        check_tableau();
//    }
//    else
//    {
//        $("span.error_nom").html('<img src="Images/croix.png" height="15px">');
//        tab_verif["nom"]=0;
//        check_tableau();
//    }
//}
//
//
//function verifier_prenom()
//{
//    var matchExp = /^[a-zA-Z][a-z]|ê|è|é|ë|à|ù|ü|û|î|ï|-+$/;
//    if($("#prenom_adherent").val().match(matchExp)){
//        $("span.error_prenom").html('<img src="Images/check.png" height="15px">');
//        tab_verif["prenom"]=1;
//        check_tableau();
//    }
//    else{
//        $("span.error_prenom").html('<img src="Images/croix.png" height="15px">');
//        tab_verif["prenom"]=0;
//        check_tableau();
//    }
//}
//
//function check_tableau()
//{
//    if ( tab_verif["fixe"]==1 && tab_verif["portable"]==1 && tab_verif["nom"]==1 && tab_verif["prenom"]==1 && tab_verif["email"]==1 && tab_verif["date_naissance"]==1 && tab_verif["licence"]==1 && tab_verif["mdp"]==1 && tab_verif["email_parrain"]==1 && tab_verif["adresse_1"]==1)
//    {
//        $(".btn_submit").removeAttr('disabled');
//    }
//    else
//    {
//        $(".btn_submit").attr("disabled","disabled");
//    }
//
//}
//
//function check_modif()
//{
//    verifier_nom();
//    verifier_prenom();
//    verifier_email();
//    verifier_fixe();
//    verifier_portable();
//    verifier_date();
//
//    if($("#licence_checkbox").is(':checked'))
//        verifier_licence();
//
//    if ( tab_verif["fixe"]==1 && tab_verif["portable"]==1 && tab_verif["nom"]==1 && tab_verif["prenom"]==1 && tab_verif["email"]==1 && tab_verif["date_naissance"]==1  && tab_verif["licence"]==1)
//        return(true);
//    else
//        return(false);
//}
//
//
//function modif_infos()
//{
//
//    if($("#new_mdp").val()!="")
//    {
//        var matchExp =/^[a-zA-Z0-9]{8,10}$/;
//        if($("#new_mdp").val().match(matchExp))
//            tab_verif["mdp"]=1;
//        else
//            $("span.erreur_new_mdp").html('<img src="Images/croix.png" height="15px">');
//    }
//    else
//        tab_verif["mdp"]=1;
//
//    if($("#new_email_adherent").val()!="")
//    {
//        var matchExp =/^[a-zA-Z0-9_.-]+@[a-z]{0,10}[.]{1}[a-z]{2,4}$/;
//        if($("#new_email_adherent").val().match(matchExp))
//            appel_ajax($("#new_email_adherent").val(), 1);
//    }
//    else
//        tab_verif["email"]=1;
//
//    if($("#licence_checkbox").is(':checked'))
//        verifier_licence();
//
//    verifier_fixe();
//    verifier_portable();
//    verifier_adresse();
//
//    if(tab_verif["fixe"]==1 && tab_verif["portable"]==1 && tab_verif["email"]==1 && tab_verif["mdp"]==1 && tab_verif["adresse_1"]==1 && tab_verif["licence"]==1)
//        return true;
//    else
//        return false;
//}
//
//
