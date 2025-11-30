# spring-boot-rest-api-advanced-project-03
Advanced Spring boot CRUD REST API using MySQL

What can you learned from To do project ?

1. Defind entity with annotations & setup docker create database using MySQL
using docker for create database mysql: docker run -d -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=tododb --name mysqldb -p 3307:3306 mysql:8.0
use this on your project: will be create: database with the name is: tododb,
username: root
pass: secret
port: 3307
localhost/mysql 

random hex: openssl rand -hex 32. Use this to put into spring.jwt.secrt: xxxxx xxxx xxxx

2. JWT (JSON Web Token)

JWT là chuỗi token gồm 3 phần:

header.payload.signature

Header: thuật toán ký (HS256)

Payload: chứa claims (subject, issuedAt, expiration, roles…)

Signature: dùng để verify token

Token được trao cho user sau khi login thành công, client phải gửi token trong mỗi request tiếp theo để xác thực.

3. Secret Key & Signing Algorithm

Ứng dụng sử dụng thuật toán HS256 (HMAC SHA-256):

Key phải đủ mạnh (tối thiểu 256-bit)

Key được lưu trong application.properties dạng Base64

Khi ký/verify JWT:

Giải mã Base64 → mảng byte

Tạo Key bằng Keys.hmacShaKeyFor(byte[])

4. Token Structure in This Project

Token được build với các thông tin:

claims: custom fields

subject: username (định danh user)

iat: thời điểm token được tạo

exp: thời điểm hết hạn (cấu hình qua property)

Ký bằng HS256

5. Generate Token Flow

Hàm generateToken:

Nhận user + custom claims

Set subject = username

Set issuedAt = now

Set expiration = now + expiration-millis

Ký bằng secret key

Trả về chuỗi JWT

7. Extract Claims

Hệ thống dùng:

extractClaim(token, Claims::getSubject)
extractClaim(token, Claims::getExpiration)


Cấu trúc quan trọng:

extractClaim (generic) → lấy bất kỳ claim

extractAllClaims → parse JWT, verify signature, trả về toàn bộ claims

Việc đọc token sẽ:

Verify signature

Check format

Trả body (payload)

6. Validate Token

Validation cần kiểm tra:

Signature có đúng không?

Token có hết hạn không? (exp)

Token có thuộc về đúng user không? (subject == user.getUsername())

Hàm cần implement:

isTokenValid(token, userDetails)

8. Expiration Check

Kiểm tra hết hạn bằng claim:

expiration.before(new Date())


Nếu token hết hạn → trả false.
9. Token Parsing

Dùng JJWT:

Jwts.parserBuilder()
    .setSigningKey(key)
    .build()
    .parseClaimsJws(token)


Thao tác này:

Giải mã token

Verify chữ ký

Ném exception nếu token hết hạn, sai key, bị sửa, sai format,…
10. Key Management

Secret key lấy từ configuration:

spring.jwt.secret=d6f6606fb1b6a3983bef8bfa1be67a9cf9b016bcd975632b570e83931fbbeb79
spring.jwt.expiration=900000


Key decode:

byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
Keys.hmacShaKeyFor(keyBytes);
11. Security Concepts To Understand
11.1. Stateless Authentication

Server không lưu session, JWT là self-contained → mọi thông tin nằm trong token.

11.2. Token signature

Bảo đảm token không bị sửa dữ liệu.

11.3. Token expiration

Hạn chế token bị lạm dụng.

11.4. Refresh tokens (dự án nâng cao)

Dùng để tạo access token mới khi access token hết hạn.
12. Exceptions You Should Know

ExpiredJwtException – token hết hạn

SignatureException – chữ ký sai

MalformedJwtException – token format sai

UnsupportedJwtException – thuật toán không hỗ trợ

IllegalArgumentException – token null/empty

Việc parse bằng parseClaimsJws() có thể ném những lỗi này.

Flow hoạt động của Todo project: 
* Flow đăng ký: POST/api/auth/register 
	Client -> AuthenticationController.register() -> Impl : kiểm tra email đã tồn tại chưa, tạo user mới, mã hóa password và gán quyền -> lưu vào DB 
* FLow đăng nhập: POST/api/auth/login 
	Client gửi email/pass -> AuthenticationControllerLogin() -> Impl: AuthenticationManager xác thực credentials, tìm user trong DB theo email, JwtService.generateToken()
	để tạo token -> trả về authenticationREsponse chứa token.
* FLow xác thực request: Mỗi request đến API trừ api/auth đều đi qua Filter:
	JWTAuthenticationFilter.doFilterInternal() : 
		+ Đọc header "Authorization: Bearer {token}
		+ Check xem có không hoặc có đúng format không, nếu không thì sẽ cho request đi tiếp ( nhưng sẽ reject ở SecurityConfig)
		+ extract token, cắt bỏ bearer 
		+ extract username, lấy email từ token 
		+ nếu chưa authenticated thì load user detail từ DB theo email 
		+ jwtService sẽ kiểm tra user từ DB theo email xem có hợp lệ hay không, user name khớp, token chưa expired 
		+ Tạo userNamPassAuthenticationToken  và set vào securityContextHolder 
		+ request tiếp tục đi đến controller 
* FLow create todo: POST/api/todos: 
	Client sẽ gửi todoRequest: title, des,... -> JWTAuthenticationFilter xác thực -> todoController.createTodo -> Impl 
	GetAuthenticatedUser: lấy user từ SecurityContextHolder -> tạo entity và gán owner authen và complete = false vào 
	cho lưu vào DB -> convert sang toDoResponse -> trả về cho client
	