<script lang="ts">
	import { page } from '$app/stores';
	import { redirect } from '@sveltejs/kit';

	if ($page.status == 404) {
		throw redirect(301, '/404');
	}

	let message = $page.error === null ? 'Unknown' : $page.error.message;
</script>

<h1>{$page.status}: {message}</h1>
