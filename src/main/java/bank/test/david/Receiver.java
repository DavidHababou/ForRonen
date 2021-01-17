package bank.test.david;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

@Component
public class Receiver {
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(Receiver.class);
	
	public CalcResult handleMessage(Request message) throws Exception{
		try{
			return new CalcResult().calculate(message.getfirstNumber(), message.getSecondNumber());
		}
		catch(Exception ex) {
			logger.error("error calculating data", ex);
			throw ex;
		}
	}
}