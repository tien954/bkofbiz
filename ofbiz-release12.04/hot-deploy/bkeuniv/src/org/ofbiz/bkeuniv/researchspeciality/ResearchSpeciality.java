package src.org.ofbiz.bkeuniv.researchspeciality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;
import src.org.ofbiz.utils.BKEunivUtils;

public class ResearchSpeciality {
	public final static String module = ResearchSpeciality.class.getName();
	
	public static String createResearchSpecialityRequestResponse(HttpServletRequest request, HttpServletResponse response){
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Locale locale = UtilHttp.getLocale(request);
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		GenericValue staff = (GenericValue)request.getSession().getAttribute("staff");
		Map<String, Object> context = FastMap.newInstance();
		context.put("researchSpecialityId",request.getParameter("researchSpecialityId"));
		context.put("researchSpecialityName",request.getParameter("researchSpecialityName"));
		try{
			Map<String, Object> resultNewResearchSpeciality = dispatcher.runSync("createResearchSpeciality", context);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(resultNewResearchSpeciality), response, 200);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "success";
	}
	
	public static Map<String, Object> getResearchSpeciality(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String researchSpecialityId = (String)context.get("researchSpecialityId");		
		String researchSpecialityName = (String)context.get("researchSpecialityName");		
		
		try {
			 Map<String, Object> result = ServiceUtil.returnSuccess();
			 EntityCondition entity;
			 
			 EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			 List<GenericValue> list;
			 if(researchSpecialityId == null) {
					list = delegator.findList("ResearchSpeciality", null, null, null, findOptions, true);					
			 }
			  else {				
				  entity = EntityCondition.makeCondition("researchSpecialityId", EntityOperator.EQUALS, researchSpecialityId);				
				  list = delegator.findList("ResearchSpeciality", entity, null, null, findOptions, true);	
			}
			 result.put("researchSpeciality", list);
			 return result;						 
	
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
  			return rs; 			
  		}				
	}
	
	public static Map<String, Object> createResearchSpeciality(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		String researchSpecialityId = (String) context.get("researchSpecialityId");
		String researchSpecialityName = (String) context.get("researchSpecialityName");
		
		GenericValue gv = delegator.makeValue("ResearchSpeciality");

		try {
			gv.put("researchSpecialityId", researchSpecialityId);
			gv.put("researchSpecialityName", researchSpecialityName);
			
			delegator.create(gv);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		retSucc.put("researchSpeciality", gv);
		retSucc.put("message", "Create new row");
		return retSucc;
	}
	
	public static Map<String, Object> deleteResearchSpeciality(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String researchSpecialityId = (String)context.get("researchSpecialityId");
        try{
        	GenericValue gv = delegator.findOne("ResearchSpeciality", UtilMisc.toMap("researchSpecialityId",researchSpecialityId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + researchSpecialityId);
        	} else {
        		retSucc.put("result", "not found record with id: " + researchSpecialityId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> updateResearchSpeciality(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String researchSpecialityId = (String) context.get("researchSpecialityId");
		String researchSpecialityName = (String) context.get("researchSpecialityName");
		
		try{
			GenericValue gv = delegator.findOne("ResearchSpeciality", false, UtilMisc.toMap("researchSpecialityId",researchSpecialityId));
			if(gv != null){
				
				
				gv.put("researchSpecialityName", researchSpecialityName);
				
				delegator.store(gv);
				
				Map<String, Object> rs = new HashMap<String, Object>();
				rs.put("researchSpecialityId", researchSpecialityId);
				rs.put("researchSpecialityName", researchSpecialityName);
				
				retSucc.put("researchSpeciality", rs);
        		retSucc.put("message", "updated record with id: " + researchSpecialityId);
        	} else {
        		retSucc.put("message", "not found record with id: " + researchSpecialityId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
}