export function POST({ cookies }) {
	cookies.delete('jwtToken');
	return new Response();
}
