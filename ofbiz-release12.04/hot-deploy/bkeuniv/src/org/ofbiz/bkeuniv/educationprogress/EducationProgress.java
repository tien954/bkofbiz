package src.org.ofbiz.bkeuniv.educationprogress;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilHttp;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.net.URL;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;

public class EducationProgress {
	public final static String module = EducationProgress.class.getName();
	
	public static String createEducationProgressRequestResponse(HttpServletRequest request, HttpServletResponse response){
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Locale locale = UtilHttp.getLocale(request);
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		GenericValue staff = (GenericValue)request.getSession().getAttribute("staff");
		System.out.println("EducationProgress::createEducatinoProgressRequestResponse, Staff = " + staff.get("staffEmail"));
		Map<String, Object> context = FastMap.newInstance();
		context.put("userLogin", userLogin);
		context.put("staffId",request.getParameter("staffId"));
		context.put("institution",request.getParameter("institution"));
		context.put("speciality",request.getParameter("speciality"));
		context.put("educationType",request.getParameter("educationType"));
		context.put("graduateDate",request.getParameter("graduateDate"));
		try{
			dispatcher.runSync("createEducationProgress", context);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "success";
	}
	
	public static Map<String, Object> getEducationProgress(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String u1 = (String)ctx.getAttribute("userLoginId");
		String u2 = (String)context.get("userLoginId");
		
		if(u1 == null) u1 = "NULL";
		if(u2 == null) u2 = "NULL";
		
		System.out.println(module + "::getEducationProgress, System.out.println u1 = " + u1 + ", u2 = " + u2);
		Debug.log(module + "::getEducationProgress, Debug.log u1 = " + u1 + ", u2 = " + u2);
		
		
		String[] keys = {"educationProgressId", "staffId", "educationType", "institution", "speciality", "graduateDate"};
		
		try {
			
			EntityCondition entity = null;
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			Map<String, Object> fields = new HashMap<String, Object>();;
			for(String key: keys) {
				Object el = context.get(key);
				if(!(el == null||el==(""))) {
					if(key=="graduateDate") {
						fields.put(key, Date.valueOf((String) el));
					} else {
						fields.put(key, el);
					}
				}
			}

			
			System.out.println( ">>>>>>>" );
			for ( Entry<String, ? extends Object> key : context.entrySet()) {
				Object a = key.getKey();
			    Object value = key.getValue();
				System.out.println( a );
				System.out.println( value );
			}
			System.out.println( "<<<<<<<" );
			
			//List<GenericValue> list = delegator.findList("EducationProgress", entity, null, null, findOptions, true);	
			List<GenericValue> list = delegator.findByAnd("EducationProgress", fields);
			Map<String, Object> result = ServiceUtil.returnSuccess();
			
			List<Map> listEducationProgress = FastList.newInstance();
			for(GenericValue el: list) {
				Map<String, Object> mapEducationProgress = FastMap.newInstance();
				mapEducationProgress.put("educationProgressId", el.getString("educationProgressId"));
				mapEducationProgress.put("staffId", el.getString("staffId"));
				mapEducationProgress.put("educationType", el.getString("educationType"));
				mapEducationProgress.put("institution", el.getString("institution"));
				mapEducationProgress.put("speciality", el.getString("speciality"));
				mapEducationProgress.put("graduateDate", el.getString("graduateDate"));
				listEducationProgress.add(mapEducationProgress);
			}
			result.put("educationProgress", listEducationProgress);
			return result;
		
		} catch (Exception e) {
			System.out.print("Education Progress Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> createEducationProgress(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		String staffId = (String) context.get("staffId");
		String educationType = (String) context.get("educationType");
		String institution = (String) context.get("institution");
		String speciality = (String) context.get("speciality");
		String graduateDate = (String) context.get("graduateDate");
		
		System.out.println("EducationProgress::createEducationProgress, staffId = " + staffId + 
				", educationType = " + educationType + ", institution = " + institution + ", speciality = " + speciality);
		
		GenericValue gv = delegator.makeValue("EducationProgress");

		gv.put("educationProgressId", delegator.getNextSeqId("EducationProgress"));

		try {
			gv.put("staffId", staffId);
			gv.put("educationType", educationType);
			gv.put("institution", institution);
			gv.put("speciality", speciality);
			gv.put("graduateDate", Date.valueOf(graduateDate));

			delegator.create(gv);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		Map<String, Object> mapEducationProgress = FastMap.newInstance();
		mapEducationProgress.put("educationProgressId", gv.getString("educationProgressId"));
		mapEducationProgress.put("staffId", staffId);
		mapEducationProgress.put("educationType", educationType);
		mapEducationProgress.put("institution", institution);
		mapEducationProgress.put("speciality", speciality);
		mapEducationProgress.put("graduateDate", graduateDate);
		
		
		retSucc.put("educationProgress", mapEducationProgress);

		return retSucc;
	}
	
	public static Map<String, Object> deleteEducationProgress(DispatchContext ctx, Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String educationProgressId = (String)context.get("educationProgressId");
        try{
        	GenericValue gv = delegator.findOne("EducationProgress", UtilMisc.toMap("educationProgressId",educationProgressId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + educationProgressId);
        	} else {
        		retSucc.put("result", "not found record with id: " + educationProgressId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> updateEducationProgress(DispatchContext ctx, Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String staffId = (String) context.get("staffId");
		String educationType = (String) context.get("educationType");
		String institution = (String) context.get("institution");
		String speciality = (String) context.get("speciality");
		String graduateDate = (String) context.get("graduateDate");
		String educationProgressId = (String) context.get("educationProgressId");
		
		try{
			GenericValue gv = delegator.findOne("EducationProgress", false, UtilMisc.toMap("educationProgressId",educationProgressId));
			if(gv != null){
				gv.put("staffId", staffId);
				gv.put("educationType", educationType);
				gv.put("institution", institution);
				gv.put("speciality", speciality);
				gv.put("graduateDate", Date.valueOf(graduateDate));
				
				delegator.store(gv);	
        		retSucc.put("result", "updated record with id: " + educationProgressId);
        	} else {
        		retSucc.put("result", "not found record with id: " + educationProgressId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
}
