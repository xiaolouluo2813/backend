package com.blockchain.server.eth.web3j.impl;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.eth.common.config.EthConfig;
import com.blockchain.server.eth.common.constants.wallet.WalletTypeConstants;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.dto.web3j.Web3jWalletDTO;
import com.blockchain.server.eth.web3j.IWalletWeb3j;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.*;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import rx.Subscription;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 关于以太坊钱包操作的工具类
 */
@Component
public class WalletWeb3jImpl extends BaseWeb3jImpl implements IWalletWeb3j {

    // 查询代币余额参数
    static final String BALANCE_OF = "balanceOf";
    // 代币交易
    static final String TRANSFER = "transfer";
    // 托管钱包地址前缀
    static final String ADDRESS_HEADER = "0x";


    // ETH转账的手续费
    @Value("${wallert.eth.gasLimit}")
    BigInteger ETH_GAS_LIMIT;
    @Value("${wallert.eth.gasPrice}")
    BigInteger ETH_GAS_PRICE;
    // TOKEN转账的手续费
    @Value("${wallert.token.gasLimit}")
    BigInteger ETH_TOKEN_GAS_LIMIT;
    @Value("${wallert.token.gasPrice}")
    BigInteger ETH_TOKEN_GAS_PRICE;

    public WalletWeb3jImpl(EthConfig ethConfig) {
        super(ethConfig);
    }

    @Override
    public Web3jWalletDTO creationEthWallet(String password) {
        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            WalletFile walletFile = org.web3j.crypto.Wallet.createStandard(password, ecKeyPair);
            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();

            String keystore = objectMapper.writeValueAsString(walletFile);  //  keystore
            String privateKey = ecKeyPair.getPrivateKey().toString(16); //  (转为16进制)私钥
            String address = ADDRESS_HEADER + walletFile.getAddress(); // 钱包地址

            Web3jWalletDTO dto = new Web3jWalletDTO();//数据对象
            dto.setAddr(address);     // 钱包地址private_key
            dto.setPrivateKey(privateKey);  // 生成私钥
            dto.setKeystore(keystore);   // keystore
            return dto;
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    @Override
    public Web3jWalletDTO updateEthWallet(String privateKey, String passwordNew) {
        try {
            //根据私钥修改密码
            ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(privateKey, 16));
            WalletFile walletFile = org.web3j.crypto.Wallet.createStandard(passwordNew, ecKeyPair);
            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            String _keystore = objectMapper.writeValueAsString(walletFile);  //  新密码生成的新的keystore
            String address = ADDRESS_HEADER + walletFile.getAddress(); // 钱包地址

            Web3jWalletDTO dto = new Web3jWalletDTO();//数据对象
            dto.setAddr(address);     // 钱包地址private_key
            dto.setPrivateKey(privateKey);  // 生成私钥
            dto.setKeystore(_keystore);   // keystore
            return dto;
        } catch (CipherException e) {
            throw new EthWalletException(EthWalletEnums.PASSWORD_ERROR);
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    @Override
    public void isPassword(String keystore, String password) {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            WalletFile walletFileVerify = objectMapper.readValue(keystore, WalletFile.class);
            Wallet.decrypt(password, walletFileVerify).getPrivateKey();
        } catch (CipherException e) {
            throw new EthWalletException(EthWalletEnums.PASSWORD_ERROR);
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    /**
     * 查询ETH余额
     *
     * @param address 钱包地址
     * @return 余额
     */
    @Override
    public BigInteger getEthBalance(String address) {
        try {
            EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            return ethGetBalance.getBalance();
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    /**
     * 查询ETH小数位
     *
     * @return 小数位
     */
    @Override
    public int getEthDecimal() {
        return ETH_DECIMALS;
    }

    @Override
    public int getTokenDecimal(String tokenAddr) {
        return 0;
    }

    /**
     * 查询代币余额
     *
     * @param addr      钱包地址
     * @param tokenAddr 代币地址
     * @return 余额
     */
    @Override
    public BigInteger getTokenBalance(String addr, String tokenAddr) {
        String methodName = BALANCE_OF;
        List inputParameters = new ArrayList<>();
        List outputParameters = new ArrayList<>();
        Address address = new Address(addr);
        inputParameters.add(address);
        TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(addr, tokenAddr, data);
        BigInteger balanceValue = BigInteger.ZERO;
        try {
            EthCall ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            return (BigInteger) results.get(0).getValue();
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    /**
     * ETH 转账
     *
     * @param fromAddr       支付地址
     * @param fromPrivateKey 支付地址私钥
     * @param toAddr         接收地址
     * @param tBalance       转账的余额
     * @param gasPrice       燃烧的费用
     * @return hash
     */
    @Override
    public String ethWalletTransfer(String fromAddr, String fromPrivateKey, String toAddr, BigInteger tBalance, BigInteger gasPrice) {
        try {
            Credentials credentials = Credentials.create(fromPrivateKey);
            EthGetTransactionCount ethGetTransactionCount =
                    web3j.ethGetTransactionCount(fromAddr, DefaultBlockParameterName.LATEST).send();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, ETH_GAS_LIMIT, toAddr, tBalance);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            return ethSendTransaction.getTransactionHash();
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    /**
     * ETH 转账
     *
     * @param fromAddr       支付地址
     * @param fromPrivateKey 支付地址私钥
     * @param toAddr         接收地址
     * @param tBalance       转账的余额
     * @return hash
     */
    @Override
    public String ethWalletTransfer(String fromAddr, String fromPrivateKey, String toAddr, BigInteger tBalance) {
        try {
            Credentials credentials = Credentials.create(fromPrivateKey);
            EthGetTransactionCount ethGetTransactionCount =
                    web3j.ethGetTransactionCount(fromAddr, DefaultBlockParameterName.LATEST).send();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, ETH_GAS_PRICE, ETH_GAS_LIMIT, toAddr, tBalance);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            return ethSendTransaction.getTransactionHash();
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }
    @Override
    public String ethWalletTransfer(String fromAddr, String fromPrivateKey, String toAddr, BigDecimal tBalance) {
        try {
        	Credentials credentials = Credentials.create(fromPrivateKey);
            TransactionReceipt transferReceipt = Transfer.sendFunds(web3j, credentials, toAddr,tBalance, Convert.Unit.WEI).send();
            return transferReceipt.getTransactionHash();
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    /**
     * 代币转账
     *
     * @param fromAddr       支付地址
     * @param tokenAddr      代币地址
     * @param fromPrivateKey 支付地址私钥
     * @param toAddr         接收地址
     * @param tBalance       转账代币余额
     * @param gasPrice       旷工费用
     * @return hash
     */
    @Override
    public String ethWalletTokenTransfer(String fromAddr, String tokenAddr, String fromPrivateKey, String toAddr, BigInteger tBalance, BigInteger gasPrice) {
        List<Type> inputParameters = Arrays.asList(new Address(toAddr), new Uint256(tBalance));
        return transact(fromAddr, fromPrivateKey, tokenAddr, TRANSFER, gasPrice, ETH_TOKEN_GAS_LIMIT, inputParameters);
    }

    /**
     * 代币转账
     *
     * @param fromAddr       支付地址
     * @param tokenAddr      代币地址
     * @param fromPrivateKey 支付地址私钥
     * @param toAddr         接收地址
     * @param tBalance       转账代币余额
     * @return hash
     */
    @Override
    public String ethWalletTokenTransfer(String fromAddr, String tokenAddr, String fromPrivateKey, String toAddr, BigInteger tBalance) {
        List<Type> inputParameters = Arrays.asList(new Address(toAddr), new Uint256(tBalance));
        return transact(fromAddr, fromPrivateKey, tokenAddr, TRANSFER, ETH_TOKEN_GAS_PRICE, ETH_TOKEN_GAS_LIMIT, inputParameters);
    }

    @Override
    public Transaction creatTx(String fromAddr, String toAddr, String tokenAddr, BigInteger amount) {
        Transaction transaction;
//        amount = BigInteger.ZERO;
        try {
            if (WalletTypeConstants.ETH.equalsIgnoreCase(tokenAddr)) {
                transaction = Transaction.createEtherTransaction(fromAddr, null, ETH_GAS_PRICE, ETH_GAS_LIMIT, toAddr, amount);
            } else {
                String methodName = TRANSFER;
                List<Type> inputParameters = new ArrayList<>();
                List<TypeReference<?>> outputParameters = new ArrayList<>();
                Address tAddress = new Address(toAddr);
                Uint256 value = new Uint256(amount);
                inputParameters.add(tAddress);
                inputParameters.add(value);
                TypeReference<Bool> typeReference = new TypeReference<Bool>() {
                };
                outputParameters.add(typeReference);
                Function function = new Function(methodName, inputParameters, outputParameters);
                String data = FunctionEncoder.encode(function);
                transaction = Transaction.createFunctionCallTransaction(fromAddr, null, ETH_TOKEN_GAS_PRICE,
                        ETH_TOKEN_GAS_LIMIT, tokenAddr, data);
            }
            return transaction;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
    }

    @Override
    public BigInteger estimateGas(String fromAddr, String toAddr, String tokenAddr, BigInteger amount) {
        Transaction transaction;
        try {
            transaction = creatTx(fromAddr, toAddr, tokenAddr, amount);
            System.out.println(JSONObject.toJSONString(Arrays.asList(transaction)));
            EthEstimateGas gas = web3j.ethEstimateGas(transaction).send();
            BigInteger gasAmount = BigInteger.ZERO;
            if (WalletTypeConstants.ETH.equalsIgnoreCase(toAddr)) {
                gasAmount = gas.getAmountUsed().multiply(ETH_GAS_PRICE);
            } else {
                gasAmount = gas.getAmountUsed().multiply(ETH_TOKEN_GAS_PRICE);
            }
            return gasAmount;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
    }

    @Override
    public BigInteger estimateGas(String fromAddr, String toAddr, String tokenAddr, BigInteger amount, BigInteger gasPrice, BigInteger gasLimit) {
        Transaction transaction;
        try {
            transaction = Transaction.createEtherTransaction(fromAddr, null, gasPrice, gasLimit, toAddr, amount);
            EthEstimateGas gas = web3j.ethEstimateGas(transaction).send();
            return gas.getAmountUsed();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
    }

}
