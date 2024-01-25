<script lang="ts">
	import { onMount } from 'svelte';
	import * as THREE from 'three';

	onMount(() => {
		const canvas: HTMLCanvasElement | null = document.querySelector('canvas.webgl');

		const scene = new THREE.Scene();

		const geometry = new THREE.BufferGeometry();

		const verticles = [];

		for (let i = 0; i < 140; i += 3) {
			for (let j = 0; j < 93; j += 2) {
				const point = [i, j, -80];
				verticles.push(...point);
			}
		}

		const vertices = new Float32Array(verticles);

		geometry.setAttribute('position', new THREE.BufferAttribute(vertices, 3));
		const material = new THREE.PointsMaterial({
			color: new THREE.Color('#fff'),
			size: 0.1
		});

		const mesh = new THREE.Points(geometry, material);
		mesh.rotateX(-1);
		mesh.translateZ(2);
		scene.add(mesh);

		const pointLight = new THREE.PointLight(0xffffff, 0.1);
		pointLight.position.x = 2;
		pointLight.position.y = 3;
		pointLight.position.z = 4;
		scene.add(pointLight);

		const sizes = {
			width: 1250,
			height: 400
		};

		window.addEventListener('resize', () => {
			sizes.width = window.innerWidth;
			camera.aspect = sizes.width / sizes.height;

			camera.updateProjectionMatrix();

			renderer.setSize(sizes.width, sizes.height);
			renderer.setPixelRatio(Math.min(window.devicePixelRatio, 2));
		});

		window.addEventListener('move', () => {});

		const camera = new THREE.PerspectiveCamera(75, sizes.width / sizes.height, 0.1, 100);
		camera.position.x = 65;
		camera.position.y = -20;
		camera.position.z = -50;
		scene.add(camera);

		// Controls
		// const controls = new OrbitControls(camera, canvas)
		// controls.enableDamping = true

		if (canvas === null) {
			return;
		}
		const renderer = new THREE.WebGLRenderer({
			canvas: canvas,
			alpha: true
		});
		renderer.setSize(sizes.width, sizes.height);
		renderer.setPixelRatio(Math.min(window.devicePixelRatio, 2));

		const tick = () => {
			renderer.render(scene, camera);
			// Call tick again on the next frame
			window.requestAnimationFrame(tick);
		};
		tick();
	});
</script>

<canvas class="webgl" />
